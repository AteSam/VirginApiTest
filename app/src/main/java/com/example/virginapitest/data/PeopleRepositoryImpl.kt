package com.example.virginapitest.data

import com.example.virginapitest.common.Results
import com.example.virginapitest.domain.PeopleRepository
import com.example.virginapitest.domain.entity.People
import retrofit2.HttpException
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val peopleService: PeopleService
): PeopleRepository {

    override suspend fun getPeople(): Results<List<People>> {
        val responseWrapper = peopleService.getPeopleResponse()
        when {
            responseWrapper.isSuccessful-> {
                val people = responseWrapper.body()?.map {
                    People(it.id, it.firstName , it.jobTitle)
                }
                return Results.Ok(people?.distinctBy { it.firstName }?.sortedBy { it.id }.orEmpty())
            }
            responseWrapper.code() == 404 -> {
                return Results.Ok(emptyList<People>())
            }
            else -> throw  HttpException(responseWrapper)
        }
    }
}