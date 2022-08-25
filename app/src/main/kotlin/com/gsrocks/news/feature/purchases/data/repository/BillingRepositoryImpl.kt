package com.gsrocks.news.feature.purchases.data.repository

import android.app.Activity
import com.gsrocks.news.core.utils.empty
import com.gsrocks.news.feature.purchases.data.datasource.BillingDataSource
import com.gsrocks.news.feature.purchases.data.mappers.toProductDetailsModel
import com.gsrocks.news.feature.purchases.domain.model.*
import com.gsrocks.news.feature.purchases.domain.repository.BillingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BillingRepositoryImpl @Inject constructor(
    private val billingDataSource: BillingDataSource
) : BillingRepository {

    override fun launchPurchase(
        activity: Activity,
        productDetails: ProductDetailsModel,
        offer: SubscriptionOffer?
    ) {
        billingDataSource.launchPurchase(activity, productDetails, offer)
    }

    override val oneTimeProductDetailsFlow: Flow<List<ProductDetailsModel>>
        get() = billingDataSource.oneTimeProductDetailsFlow.map { list ->
            list.map {
                ProductDetailsModel(
                    productId = it.productDetails.productId,
                    productType = ProductType.fromString(it.productDetails.productType),
                    name = it.productDetails.name,
                    title = it.productDetails.title,
                    description = it.productDetails.description,
                    formattedPrice = it.productDetails.oneTimePurchaseOfferDetails?.formattedPrice
                        ?: String.empty,
                    state = it.productState,
                    subscriptionOffers = emptyList()
                )
            }
        }

    override val subscriptionDetailsFlow: Flow<List<ProductDetailsModel>>
        get() = billingDataSource.subscriptionDetailsFlow.map { list ->
            list.map { it.toProductDetailsModel() }
        }

    override val billingInProcessFlow: Flow<Boolean>
        get() = billingDataSource.billingInProcessFlow

    override val purchaseErrorFlow: Flow<Throwable>
        get() = billingDataSource.purchaseErrorFlow

    override suspend fun getActiveSubscriptions(): List<ProductDetailsModel> {
        return billingDataSource.getActiveSubscriptions().map { it.toProductDetailsModel() }
    }
}
