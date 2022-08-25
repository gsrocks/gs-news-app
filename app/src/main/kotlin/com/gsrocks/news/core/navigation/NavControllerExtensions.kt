package com.gsrocks.news.core.navigation

import androidx.navigation.NavController

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(event.route)
}