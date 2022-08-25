package com.gsrocks.news.feature.purchases.presentation

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrocks.news.feature.purchases.domain.model.ProductDetailsModel
import com.gsrocks.news.feature.purchases.domain.model.SubscriptionOffer
import com.gsrocks.news.feature.purchases.domain.repository.BillingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProductsViewModel"

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val billingRepository: BillingRepository
) : ViewModel() {

    var productsDetails by mutableStateOf<List<ProductDetailsModel>>(emptyList())
        private set

    var subscriptionDetails by mutableStateOf<List<ProductDetailsModel>>(emptyList())

    var isLoading by mutableStateOf(false)
        private set

    private val _snackbarChannel = Channel<String>(Channel.BUFFERED)
    val showSnackbarFlow = _snackbarChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            billingRepository.oneTimeProductDetailsFlow.collectLatest {
                productsDetails = it
            }
        }
        viewModelScope.launch {
            billingRepository.subscriptionDetailsFlow.collectLatest {
                subscriptionDetails = it
            }
        }
        viewModelScope.launch {
            billingRepository.billingInProcessFlow.collect {
                isLoading = it
            }
        }
        viewModelScope.launch {
            billingRepository.purchaseErrorFlow.collect {
                viewModelScope.launch {
                    _snackbarChannel.send("Oops, on error happened\n${it.message}")
                }
            }
        }
    }

    fun launchPurchase(
        activity: Activity,
        productDetails: ProductDetailsModel,
        offer: SubscriptionOffer? = null
    ) {
        billingRepository.launchPurchase(activity, productDetails, offer)
    }
}
