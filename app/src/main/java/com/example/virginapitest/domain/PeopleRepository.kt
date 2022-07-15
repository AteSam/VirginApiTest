package com.example.virginapitest.domain

import com.example.virginapitest.common.Results
import com.example.virginapitest.domain.entity.People

interface PeopleRepository {
    suspend fun getPeople(): Results<List<People>>
}