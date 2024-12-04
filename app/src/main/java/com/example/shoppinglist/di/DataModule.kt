package com.example.shoppinglist.di

import android.app.Application
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.ShopListDao
import com.example.shoppinglist.data.ShopListMapper
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ApplicationScope
    @Provides
    fun provideShopListDao( application: Application) : ShopListDao{
        return AppDatabase.getInstance(application).shopListDao()
    }

    @ApplicationScope
    @Provides
    fun provideMapper( ) : ShopListMapper{
        return ShopListMapper()
    }
}