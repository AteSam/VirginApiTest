package com.example.virginapitest.data

import com.example.virginapitest.common.Results
import com.example.virginapitest.domain.RoomsRepository
import com.example.virginapitest.domain.entity.Rooms
import retrofit2.HttpException
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val roomsService: RoomsService
): RoomsRepository {

    override suspend fun getRooms(): Results<List<Rooms>> {
        val responseWrapper = roomsService.getRoomsResponse()
        when {
            responseWrapper.isSuccessful-> {
                val people = responseWrapper.body()?.map {
                    Rooms(it.name, it.max_occupancy , it.is_occupied)
                }
                return Results.Ok(people?.distinctBy { it.name }?.sortedBy { it.max_occupancy }.orEmpty())
            }
            responseWrapper.code() == 404 -> {
                return Results.Ok(emptyList<Rooms>())
            }
            else -> throw  HttpException(responseWrapper)
        }
    }
}