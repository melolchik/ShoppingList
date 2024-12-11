package com.example.shoppinglist.di

import android.app.Application
import com.example.shoppinglist.data.ShopListProvider
import com.example.shoppinglist.presentation.MainActivity
import com.example.shoppinglist.presentation.ShopItemActivity
import com.example.shoppinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(modules = [DomainModule::class, ViewModelModule:: class, DataModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(provider: ShopListProvider)

    fun inject(fragment: ShopItemFragment)
    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}