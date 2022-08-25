package com.gsrocks.news.feature.purchases.domain.model

enum class BillingPeriod(val title: String) {
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    companion object {
        fun fromString(value: String): BillingPeriod {
            return when (value) {
                "P1W" -> WEEK
                "P1M" -> MONTH
                "P1Y" -> YEAR
                else -> throw IllegalArgumentException("Billing period not recognized")
            }
        }
    }
}
