package com.example.virginapitest.data

import com.example.virginapitest.data.model.People
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PeopleService {

    @GET("people")
    suspend fun getPeopleResponse(): Response<List<People>>

    companion object {
        fun buildService(endpoint:String,
                         okHttpClient: OkHttpClient,
                         retrofit: Retrofit
        ):PeopleService = retrofit
            .newBuilder()
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl(endpoint)
            .client(okHttpClient)
            .build()
            .create(PeopleService::class.java)
    }
}