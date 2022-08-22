package com.gsrocks.newsapp.core.navigation

import androidx.navigation.NavController

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(event.route)
}