package com.example.virginapitest.data.model

import com.google.gson.annotations.SerializedName

data class People(
    @SerializedName("id") val id: Int,
    @SerializedName("firstName")  val firstName: String,
    @SerializedName("email") val email: String,
    @SerializedName("favouriteColor") val favouriteColor: String,
    @SerializedName("jobTitle") val jobTitle: String,
    @SerializedName("phone") val phone: String
)
