package com.gsrocks.news.feature.purchases.domain.model

data class ProductDetailsModel(
    val productId: String,
    val productType: ProductType,
    val name: String,
    val title: String,
    val description: String,
    val formattedPrice: String,
    val state: ProductState,
    val subscriptionOffers: List<SubscriptionOffer>
)
