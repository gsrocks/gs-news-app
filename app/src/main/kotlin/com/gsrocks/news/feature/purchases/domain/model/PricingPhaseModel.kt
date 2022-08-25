package com.gsrocks.news.feature.purchases.domain.model

data class PricingPhaseModel(
    val priceCurrencyCode: String,
    val formattedPrice: String,
    val billingPeriod: BillingPeriod
)
