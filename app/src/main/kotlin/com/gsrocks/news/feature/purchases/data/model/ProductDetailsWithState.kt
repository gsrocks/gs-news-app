package com.gsrocks.news.feature.purchases.data.model

import com.android.billingclient.api.ProductDetails
import com.gsrocks.news.feature.purchases.domain.model.ProductState

data class ProductDetailsWithState(
    val productDetails: ProductDetails,
    val productState: ProductState
)
