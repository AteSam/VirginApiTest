package com.example.virginapitest.domain

import com.example.virginapitest.common.Results
import com.example.virginapitest.domain.entity.Rooms

interface RoomsRepository {
    suspend fun getRooms(): Results<List<Rooms>>
}