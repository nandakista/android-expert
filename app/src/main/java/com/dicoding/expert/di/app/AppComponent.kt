package com.dicoding.expert.di.app

import com.dicoding.expert.di.core.CoreComponent
import com.dicoding.expert.ui.pages.detail.DetailUserActivity
import com.dicoding.expert.ui.pages.favorite.FavoriteActivity
import com.dicoding.expert.ui.pages.home.HomeActivity
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)

interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(activity: FavoriteActivity)
    fun inject(activity: DetailUserActivity)
    fun inject(activity: HomeActivity)
}