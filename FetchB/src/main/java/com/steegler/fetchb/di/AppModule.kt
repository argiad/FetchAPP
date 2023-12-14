package com.steegler.fetchb.di

import android.content.Context
import com.steegler.fetchb.domain.StateMachine
import com.steegler.fetchlib.FetchLib
import com.steegler.fetchlib.FetchLibBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLib(@ApplicationContext appContext: Context): FetchLib {
        return FetchLibBuilder.withContext(appContext).build()
    }

    @Singleton
    @Provides
    fun provideStatMachine(lib: FetchLib): StateMachine {
        return StateMachine(lib)
    }
}