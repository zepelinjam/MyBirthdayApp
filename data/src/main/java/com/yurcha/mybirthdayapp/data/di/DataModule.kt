package com.yurcha.mybirthdayapp.data.di

import android.content.Context
import androidx.room.Room
import com.yurcha.mybirthdayapp.data.repository.BabyRepositoryImpl
import com.yurcha.mybirthdayapp.data.room.AppDatabase
import com.yurcha.mybirthdayapp.domain.repository.BabyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DataModule.Declarations::class])
@InstallIn(SingletonComponent::class)
class DataModule {

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class Declarations {

        @Binds
        abstract fun provideBabyRepository(repository: BabyRepositoryImpl): BabyRepository

    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext app: Context): AppDatabase = Room
        .databaseBuilder(app, AppDatabase::class.java, "database")
        .fallbackToDestructiveMigration()
        .build()
}