package com.example.virginapitest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virginapitest.common.Results
import com.example.virginapitest.domain.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class
PeopleViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository
): ViewModel() {

    private val _peopleEvent = MutableStateFlow<PeopleEvent>(PeopleEvent.PersonLoading)
    val peopleEvent: StateFlow<PeopleEvent>
        get() = _peopleEvent

    fun submitAction(peopleItemAction: PeopleItemAction) = when(peopleItemAction) {
        is PeopleItemAction.FetchPerson -> getPeople()
        is PeopleItemAction.PersonItemClicked -> _peopleEvent.value =
            PeopleEvent.PersonItemClickEvent(peopleItemAction.person)
    }

    private fun getPeople() {
        viewModelScope.launch {
            _peopleEvent.value = PeopleEvent.PersonLoading
            when(val results = peopleRepository.getPeople()) {
                is Results.Ok -> {
                    _peopleEvent.value = PeopleEvent.PersonLoaded(results.data)
                }
                is Results.Error -> {
                    _peopleEvent.value = PeopleEvent.PersonError
                }
            }
        }
    }
}