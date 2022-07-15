package com.example.virginapitest.injection

import com.example.virginapitest.data.PeopleRepositoryImpl
import com.example.virginapitest.data.PeopleService
import com.example.virginapitest.data.RoomsRepositoryImpl
import com.example.virginapitest.data.RoomsService
import com.example.virginapitest.domain.PeopleRepository
import com.example.virginapitest.domain.RoomsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PeopleDaggerModule {

    @Provides
    fun provideOkHttpBuilder(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.NONE }
            ).build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient):
            Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl("https://61d6afbe35f71e0017c2e74b.mockapi.io/api/v1/")
        .build()

    @Provides
    @Singleton
    fun providePeopleService(okHttpClient: OkHttpClient, retrofit: Retrofit):PeopleService =
        PeopleService.buildService(
            endpoint = "https://61d6afbe35f71e0017c2e74b.mockapi.io/api/v1/",
            okHttpClient = okHttpClient,
            retrofit = retrofit
        )

    @Provides
    @Singleton
    fun providePeopleRepository(peopleService: PeopleService): PeopleRepository =
        PeopleRepositoryImpl(peopleService)

    @Provides
    @Singleton
    fun provideRoomService(okHttpClient: OkHttpClient, retrofit: Retrofit): RoomsService =
        RoomsService.buildService(
            endpoint = "https://61d6afbe35f71e0017c2e74b.mockapi.io/api/v1/",
            okHttpClient = okHttpClient,
            retrofit = retrofit
        )

    @Provides
    @Singleton
    fun provideRoomsRepository(roomsService: RoomsService): RoomsRepository =
        RoomsRepositoryImpl(roomsService)
}