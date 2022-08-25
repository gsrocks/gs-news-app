package com.gsrocks.news.feature.purchases.data.model

import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase

data class PurchaseEvent(
    val billingResult: BillingResult,
    val purchases: List<Purchase>?
)
