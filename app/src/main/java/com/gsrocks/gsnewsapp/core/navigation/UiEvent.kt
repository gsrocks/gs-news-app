package com.gsrocks.gsnewsapp.core.navigation

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
}
