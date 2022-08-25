package com.gsrocks.news.feature.purchases.domain.model

data class SubscriptionOffer(
    val token: String,
    val pricingPhases: List<PricingPhaseModel>
)
