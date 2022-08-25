package com.gsrocks.news.feature.purchases.di

import com.gsrocks.news.feature.purchases.data.repository.BillingRepositoryImpl
import com.gsrocks.news.feature.purchases.domain.repository.BillingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PurchasesModule {

    @Binds
    fun bindBillingRepository(billingRepositoryImpl: BillingRepositoryImpl): BillingRepository
}
