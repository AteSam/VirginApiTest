package com.example.virginapitest.data.model

import com.google.gson.annotations.SerializedName

data class Rooms(
    @SerializedName("name") val name: String,
    @SerializedName("max_occupancy") val max_occupancy: Int,
    @SerializedName("is_occupied") val is_occupied: Boolean
)
