package com.gsrocks.news.feature.purchases.domain.model

enum class ProductType {
    ONE_TIME,
    SUBSCRIPTION;

    companion object {
        fun fromString(string: String): ProductType {
            return when (string) {
                "inapp" -> ONE_TIME
                "subs" -> SUBSCRIPTION
                else -> throw IllegalArgumentException("No such type")
            }
        }
    }
}
