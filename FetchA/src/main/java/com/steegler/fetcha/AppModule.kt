package com.steegler.fetcha

import android.content.Context
import androidx.room.Room
import com.steegler.fetcha.data.AppDatabase
import com.steegler.fetcha.data.RepoImpl
import com.steegler.fetcha.data.dao.DataItemDAO
import com.steegler.fetcha.domain.Repo
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
    fun provideDataDAO(appDatabase: AppDatabase): DataItemDAO {
        return appDatabase.dataItemDAO()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "Fetch"
        )
            .build()
    }


//
//    @Singleton
//    @Provides
//    fun provideDataDAO(@ApplicationContext appContext: Context): DataItemDAO {
//        return provideAppDatabase(appContext).dataItemDAO()
//    }
//
//    @Singleton
//    @Provides
//    fun provideAppDatabase(appContext: Context): AppDatabase {
//        return Room.databaseBuilder(
//            appContext,
//            AppDatabase::class.java,
//            "Fetch"
//        )
//            .build()
//    }

    @Singleton
    @Provides
    fun provideRepo(lib: FetchLib, dao: DataItemDAO): Repo {
        return RepoImpl(lib, dao)
    }
}