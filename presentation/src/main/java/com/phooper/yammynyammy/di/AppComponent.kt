package com.phooper.yammynyammy.di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
val appComponent = listOf(
    firebaseModule,
    viewModelModule,
    useCaseModule,
    repositoryModule,
    apiModule,
    networkModule,
    roomModule
)