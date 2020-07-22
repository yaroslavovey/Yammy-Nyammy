package com.phooper.yammynyammy.di.modules

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_PresenterModule::class])
abstract class PresenterModule