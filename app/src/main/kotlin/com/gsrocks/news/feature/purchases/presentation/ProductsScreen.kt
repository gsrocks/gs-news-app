package com.gsrocks.news.feature.purchases.presentation

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.gsrocks.news.core.presentation.composables.LoadingDialog
import com.gsrocks.news.feature.purchases.presentation.components.ProductItem
import com.gsrocks.news.feature.purchases.presentation.components.SubscriptionItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    onShowSnackbar: (String) -> Unit
) {
    LaunchedEffect(true) {
        viewModel.showSnackbarFlow.collect {
            onShowSnackbar(it)
        }
    }

    val activity = LocalContext.current as Activity
    val scope = rememberCoroutineScope()

    if (viewModel.isLoading) {
        Log.i("ProductsScreen", "isLoading")
        LoadingDialog()
    }

    val pagerState = rememberPagerState()

    val pages = listOf("Purchases", "Subscriptions")

    Column {
        TabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            // Add tabs for all of our pages
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                )
            }
        }

        HorizontalPager(
            count = pages.size,
            state = pagerState,
        ) { page ->
            when (page) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        items(viewModel.productsDetails) {
                            ProductItem(
                                title = it.name,
                                price = it.formattedPrice,
                                state = it.state.name,
                                modifier = Modifier.clickable {
                                    viewModel.launchPurchase(activity, it)
                                }
                            )
                        }
                    }
                }
                1 -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        items(viewModel.subscriptionDetails) { subscription ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                subscription.subscriptionOffers.forEach { offer ->
                                    SubscriptionItem(
                                        title = subscription.name,
                                        price = offer.pricingPhases.first().formattedPrice,
                                        state = subscription.state.name,
                                        modifier = Modifier.clickable {
                                            viewModel.launchPurchase(activity, subscription, offer)
                                        },
                                        renewalPeriod = offer.pricingPhases.first().billingPeriod.title
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}