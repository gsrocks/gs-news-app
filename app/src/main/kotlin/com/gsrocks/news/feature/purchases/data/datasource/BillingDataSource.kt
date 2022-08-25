package com.gsrocks.news.feature.purchases.data.datasource

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.android.billingclient.api.*
import com.gsrocks.news.core.utils.empty
import com.gsrocks.news.feature.purchases.data.model.ProductDetailsWithState
import com.gsrocks.news.feature.purchases.data.model.PurchaseEvent
import com.gsrocks.news.feature.purchases.data.model.exception.PurchaseException
import javax.inject.Inject
import com.gsrocks.news.feature.purchases.data.security.LocalSecurity
import com.gsrocks.news.feature.purchases.domain.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Singleton

private const val TAG = "BillingDataSource"

@Singleton
class BillingDataSource @Inject constructor(
    application: Application,
) : DefaultLifecycleObserver, PurchasesUpdatedListener, BillingClientStateListener {

    private val _oneTimeProductDetailsFlow =
        MutableStateFlow<List<ProductDetailsWithState>>(emptyList())
    val oneTimeProductDetailsFlow = _oneTimeProductDetailsFlow.asStateFlow()

    private val _subscriptionDetailsFlow =
        MutableStateFlow<List<ProductDetailsWithState>>(emptyList())
    val subscriptionDetailsFlow = _subscriptionDetailsFlow.asStateFlow()

    private val _purchaseEventFlow = MutableSharedFlow<PurchaseEvent>()
    val purchaseEventFlow: SharedFlow<PurchaseEvent> = _purchaseEventFlow

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val purchaseErrorFlow: SharedFlow<Throwable> = _errorFlow

    private val _billingInProcessFlow = MutableStateFlow(false)
    val billingInProcessFlow: Flow<Boolean> = _billingInProcessFlow.asStateFlow()

    private val billingClient = BillingClient.newBuilder(application)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    private val scope: CoroutineScope = GlobalScope

    private var purchases = mutableListOf<Purchase>()

    init {
        billingClient.startConnection(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (!_billingInProcessFlow.value && billingClient.isReady) {
            scope.launch {
                refreshPurchases()
            }
        }
    }

    override fun onBillingServiceDisconnected() {
        retryConnection()
    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        val responseCode = billingResult.responseCode
        val debugMessage = billingResult.debugMessage
        Log.d(TAG, "onBillingSetupFinished: $responseCode $debugMessage")
        when (responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                scope.launch {
                    queryProducts()
                    refreshPurchases()
                }
            }
            else -> retryConnection()
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        Log.i(
            TAG,
            "onPurchaseUpdated: ${billingResult.responseCode.toBillingResponseCodeName()}; purchases: ${purchases?.joinToString()}"
        )
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            for (purchase in purchases) {
                val allProducts = oneTimeProductDetailsFlow.value + subscriptionDetailsFlow.value
                val firstProduct = allProducts.first {
                    purchase.products.contains(it.productDetails.productId)
                }
                scope.launch {
                    handlePurchase(purchase, firstProduct.productDetails.productType)
                }
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // do nothing
        } else {
            scope.launch {
                _errorFlow.emit(
                    PurchaseException(
                        responseCode = billingResult.responseCode.toBillingResponseCodeName(),
                        debugMessage = billingResult.debugMessage
                    )
                )
            }
        }
        scope.launch {
            _billingInProcessFlow.emit(false)
        }
    }

    fun launchPurchase(
        activity: Activity,
        productDetailsModel: ProductDetailsModel,
        offer: SubscriptionOffer?
    ) {
        val allProducts = oneTimeProductDetailsFlow.value + subscriptionDetailsFlow.value
        val product =
            allProducts.firstOrNull { it.productDetails.productId == productDetailsModel.productId }

        if (product != null) {
            val detailsParamBuilder = BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(product.productDetails)
            if (productDetailsModel.productType == ProductType.SUBSCRIPTION && offer != null) {
                Log.d(TAG, product.productDetails.subscriptionOfferDetails!!.joinToString())
                detailsParamBuilder.setOfferToken(offer.token)
            }
            val productDetailsParamsList = listOf(detailsParamBuilder.build())

            val billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build()

            val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    scope.launch {
                        _billingInProcessFlow.emit(true)
                    }
                }
                BillingClient.BillingResponseCode.USER_CANCELED -> {
                    // do nothing
                }
                else -> {
                    scope.launch {
                        _errorFlow.emit(
                            PurchaseException(
                                responseCode = billingResult.responseCode.toBillingResponseCodeName(),
                                debugMessage = billingResult.debugMessage
                            )
                        )
                    }
                }
            }
        }
    }

    private suspend fun refreshPurchases() {
        val inAppParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()
        val subsParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        val inAppPurchasesResult = billingClient.queryPurchasesAsync(inAppParams)
        if (inAppPurchasesResult.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            inAppPurchasesResult.purchasesList.forEach {
                handlePurchase(it, BillingClient.ProductType.INAPP)
            }
        }
        val subsPurchasesResult = billingClient.queryPurchasesAsync(subsParams)
        if (subsPurchasesResult.billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            subsPurchasesResult.purchasesList.forEach {
                handlePurchase(it, BillingClient.ProductType.SUBS)
            }
        }
    }

    private suspend fun queryProducts() = withContext(Dispatchers.IO) {
        val inAppParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                oneTimeProductSkus.map { productId ->
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                }
            )
            .build()
        val subsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                subscriptionSkus.map { productId ->
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                }
            )
            .build()

        launch {
            try {
                val inAppResult = billingClient.queryProductDetails(inAppParams)
                val productDetailsWithState =
                    inAppResult.productDetailsList?.mapToProductDetailsWithState() ?: emptyList()
                _oneTimeProductDetailsFlow.emit(productDetailsWithState)
            } catch (e: Throwable) {
                _errorFlow.emit(e)
            }
        }

        launch {
            try {
                val subsResult = billingClient.queryProductDetails(subsParams)
                Log.d(TAG, subsResult.toString())
                val productDetailsWithState =
                    subsResult.productDetailsList?.mapToProductDetailsWithState() ?: emptyList()
                _subscriptionDetailsFlow.emit(productDetailsWithState)
            } catch (e: Throwable) {
                _errorFlow.emit(e)
            }
        }
    }

    private suspend fun handlePurchase(purchase: Purchase, type: String) {
        val state = when (purchase.purchaseState) {
            Purchase.PurchaseState.PURCHASED -> {
                if (!isSignatureValid(purchase)) {
                    Log.e(
                        TAG,
                        "Invalid signature. Check to make sure your " +
                                "public key is correct."
                    )
                    ProductState.PURCHASED
                } else if (!purchase.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                    val ackPurchaseResult = withContext(Dispatchers.IO) {
                        billingClient.acknowledgePurchase(acknowledgePurchaseParams.build())
                    }
                    if (ackPurchaseResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        ProductState.PURCHASED_AND_ACKNOWLEDGED
                    } else {
                        ProductState.PURCHASED
                    }
                } else {
                    ProductState.PURCHASED_AND_ACKNOWLEDGED
                }
            }
            Purchase.PurchaseState.PENDING -> ProductState.PENDING
            else -> ProductState.UNPURCHASED
        }

        val updatedPurchases = purchases.map {
            if (it.orderId == purchase.orderId) purchase
            else it
        }.toMutableList()
        if (!updatedPurchases.any { it.orderId == purchase.orderId }) {
            updatedPurchases.add(purchase)
        }
        purchases = updatedPurchases

        if (type == BillingClient.ProductType.INAPP) {
            val updateProducts = oneTimeProductDetailsFlow.value.map {
                if (purchase.products.contains(it.productDetails.productId)) it.copy(productState = state)
                else it
            }
            _oneTimeProductDetailsFlow.emit(updateProducts)
        } else if (type == BillingClient.ProductType.SUBS) {
            val updateProducts = subscriptionDetailsFlow.value.map {
                if (purchase.products.contains(it.productDetails.productId)) it.copy(productState = state)
                else it
            }
            _subscriptionDetailsFlow.emit(updateProducts)
        }
    }

    private fun retryConnection() {
        scope.launch {
            delay(RECONNECT_TIMER_START_MILLS)
            billingClient.startConnection(this@BillingDataSource)
        }
    }

    private fun isSignatureValid(purchase: Purchase): Boolean {
        return LocalSecurity.verifyPurchase(purchase.originalJson, purchase.signature)
    }

    private fun List<ProductDetails>.mapToProductDetailsWithState(): List<ProductDetailsWithState> {
        return this.map { details ->
            val purchase =
                purchases.singleOrNull { it.products.contains(details.productId) }
            val productState = when (purchase?.purchaseState) {
                Purchase.PurchaseState.PURCHASED -> {
                    if (purchase.isAcknowledged) ProductState.PURCHASED_AND_ACKNOWLEDGED
                    else ProductState.PURCHASED
                }
                Purchase.PurchaseState.PENDING -> ProductState.PENDING
                else -> ProductState.UNPURCHASED
            }
            ProductDetailsWithState(
                productDetails = details,
                productState = productState
            )
        }
    }

    private fun Int.toBillingResponseCodeName(): String {
        return when (this) {
            BillingClient.BillingResponseCode.OK -> "OK"
            BillingClient.BillingResponseCode.BILLING_UNAVAILABLE -> "BILLING_UNAVAILABLE"
            BillingClient.BillingResponseCode.ITEM_NOT_OWNED -> "ITEM_NOT_OWNED"
            BillingClient.BillingResponseCode.DEVELOPER_ERROR -> "DEVELOPER_ERROR"
            BillingClient.BillingResponseCode.ERROR -> "ERROR"
            BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED -> "FEATURE_NOT_SUPPORTED"
            BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED -> "ITEM_ALREADY_OWNED"
            BillingClient.BillingResponseCode.ITEM_UNAVAILABLE -> "ITEM_UNAVAILABLE"
            BillingClient.BillingResponseCode.SERVICE_DISCONNECTED -> "SERVICE_DISCONNECTED"
            BillingClient.BillingResponseCode.SERVICE_TIMEOUT -> "SERVICE_TIMEOUT"
            BillingClient.BillingResponseCode.SERVICE_UNAVAILABLE -> "SERVICE_UNAVAILABLE"
            BillingClient.BillingResponseCode.USER_CANCELED -> "USER_CANCELED"
            else -> String.empty
        }
    }

    companion object {
        private const val RECONNECT_TIMER_START_MILLS = 1000L
        private const val RECONNECT_TIMER_MAX_TIME_MILLIS = 15L * 60L * 1000L
        private const val SKU_DETAILS_REQUERY_TIME = 4L * 60L * 60L * 1000L

        val oneTimeProductSkus = listOf("one_time_support_level_1")
        val subscriptionSkus = listOf("regular_support")
    }
}
