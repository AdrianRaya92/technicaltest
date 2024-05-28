package com.adrianraya.technicaltest.di

import android.app.Application
import androidx.room.Room
import com.adrianraya.data.users.datasource.UsersLocalDataSource
import com.adrianraya.data.users.datasource.UsersRemoteDataSource
import com.adrianraya.domain.UserApiData
import com.adrianraya.technicaltest.data.database.UsersDatabase
import com.adrianraya.technicaltest.data.database.UsersRoomDataSource
import com.adrianraya.technicaltest.data.server.RemoteDatasource
import com.adrianraya.technicaltest.data.server.UserAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @ApiUrl
    fun provideApiUrl(): String = UserApiData.url

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room.databaseBuilder(
        app,
        UsersDatabase::class.java,
        UserApiData.database
    ).build()

    @Provides
    @Singleton
    fun provideUsersDao(db: UsersDatabase) = db.usersDao()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(@ApiUrl apiUrl: String, okHttpClient: OkHttpClient): UserAPI {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun bindLocalDataSource(localDataSource: UsersRoomDataSource): UsersLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDatasource): UsersRemoteDataSource
}