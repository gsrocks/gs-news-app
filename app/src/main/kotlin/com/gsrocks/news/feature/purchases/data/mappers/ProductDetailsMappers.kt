package com.gsrocks.news.feature.purchases.data.mappers

import com.gsrocks.news.core.utils.empty
import com.gsrocks.news.feature.purchases.data.model.ProductDetailsWithState
import com.gsrocks.news.feature.purchases.domain.model.*

fun ProductDetailsWithState.toProductDetailsModel(): ProductDetailsModel {
    return ProductDetailsModel(
        productId = productDetails.productId,
        productType = ProductType.fromString(productDetails.productType),
        name = productDetails.name,
        title = productDetails.title,
        description = productDetails.description,
        formattedPrice = productDetails.oneTimePurchaseOfferDetails?.formattedPrice
            ?: String.empty,
        state = productState,
        subscriptionOffers = productDetails.subscriptionOfferDetails?.map { offer ->
            SubscriptionOffer(
                token = offer.offerToken,
                pricingPhases = offer.pricingPhases.pricingPhaseList.map { phase ->
                    PricingPhaseModel(
                        priceCurrencyCode = phase.priceCurrencyCode,
                        formattedPrice = phase.formattedPrice,
                        billingPeriod = BillingPeriod.fromString(phase.billingPeriod)
                    )
                }
            )
        } ?: emptyList()
    )
}
