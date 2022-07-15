package com.example.virginapitest.viewmodel

import com.example.virginapitest.domain.entity.People

sealed class PeopleItemAction {
    object FetchPerson:PeopleItemAction()
    data class PersonItemClicked(val person: People):PeopleItemAction()
}
