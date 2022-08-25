package com.gsrocks.news.feature.news.presentation.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.gsrocks.news.R

sealed class HomeScreenTab(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector
) {
    object BreakingNews : HomeScreenTab(
        "breaking-news",
        R.string.breaking,
        Icons.Default.Feed
    )

    object SearchNews : HomeScreenTab(
        "search-news",
        R.string.search,
        Icons.Default.Search
    )

    object FavouriteNews : HomeScreenTab(
        "favourite-news",
        R.string.favourite,
        Icons.Default.Favorite
    )

    object Purchases : HomeScreenTab(
        "purchases",
        R.string.purchases,
        Icons.Default.Payment
    )
}
