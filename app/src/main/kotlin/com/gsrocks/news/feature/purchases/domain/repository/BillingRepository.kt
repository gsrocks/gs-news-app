package com.gsrocks.news.feature.purchases.domain.repository

import android.app.Activity
import com.gsrocks.news.feature.purchases.domain.model.ProductDetailsModel
import com.gsrocks.news.feature.purchases.domain.model.SubscriptionOffer
import kotlinx.coroutines.flow.Flow

interface BillingRepository {
    fun launchPurchase(
        activity: Activity,
        productDetails: ProductDetailsModel,
        offer: SubscriptionOffer?
    )

    val oneTimeProductDetailsFlow: Flow<List<ProductDetailsModel>>

    val subscriptionDetailsFlow: Flow<List<ProductDetailsModel>>

    val billingInProcessFlow: Flow<Boolean>

    val purchaseErrorFlow: Flow<Throwable>

    suspend fun getActiveSubscriptions(): List<ProductDetailsModel>
}
