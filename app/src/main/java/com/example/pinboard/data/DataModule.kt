package com.example.pinboard.data

import com.example.pinboard.data.api.ApiModule
import com.example.pinboard.data.repo.PinsRepository
import com.example.pinboard.data.repo.PinsRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun providePinsRepository(pinsRepositoryImpl: PinsRepositoryImpl):
            PinsRepository
}