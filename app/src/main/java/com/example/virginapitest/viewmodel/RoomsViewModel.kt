package com.example.virginapitest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virginapitest.common.Results
import com.example.virginapitest.domain.RoomsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomsRepository: RoomsRepository
): ViewModel() {

    private val _roomsEvent = MutableStateFlow<RoomsEvent>(RoomsEvent.RoomLoading)
    val roomsEvent: StateFlow<RoomsEvent>
        get() = _roomsEvent

    fun submitAction(roomsItemAction: RoomsItemAction) = when(roomsItemAction) {
        is RoomsItemAction.FetchRoom -> getRooms()
        is RoomsItemAction.RoomItemClicked -> _roomsEvent.value =
            RoomsEvent.RoomItemClickEvent(roomsItemAction.room)
    }

    private fun getRooms() {
        viewModelScope.launch {
            _roomsEvent.value = RoomsEvent.RoomLoading
            when(val results = roomsRepository.getRooms()) {
                is Results.Ok -> {
                    _roomsEvent.value = RoomsEvent.RoomLoaded(results.data)
                }
                is Results.Error -> {
                    _roomsEvent.value = RoomsEvent.RoomError
                }
            }
        }
    }
}