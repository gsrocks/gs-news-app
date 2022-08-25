package com.gsrocks.news.feature.purchases.data.model.exception

class PurchaseException(
    responseCode: String,
    debugMessage: String
) : Exception(
    "$responseCode $debugMessage"
)
