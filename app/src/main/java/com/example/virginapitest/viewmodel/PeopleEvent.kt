package com.example.virginapitest.viewmodel

import com.example.virginapitest.domain.entity.People

sealed class PeopleEvent {
    data class PersonLoaded(val data: List<People>) :PeopleEvent()
    object PersonLoading :PeopleEvent()
    object PersonError :PeopleEvent()
    data class PersonItemClickEvent(val person:People):PeopleEvent()
}