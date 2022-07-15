package com.example.virginapitest.data

import com.example.virginapitest.data.model.Rooms
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RoomsService {

    @GET("rooms")
    suspend fun getRoomsResponse(): Response<List<Rooms>>

    companion object {
        fun buildService(endpoint:String,
                         okHttpClient: OkHttpClient,
                         retrofit: Retrofit
        ):RoomsService = retrofit
            .newBuilder()
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl(endpoint)
            .client(okHttpClient)
            .build()
            .create(RoomsService::class.java)
    }
}