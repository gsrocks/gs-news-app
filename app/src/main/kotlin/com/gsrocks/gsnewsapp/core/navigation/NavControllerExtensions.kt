package com.gsrocks.gsnewsapp.core.navigation

import androidx.navigation.NavController

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(event.route)
}