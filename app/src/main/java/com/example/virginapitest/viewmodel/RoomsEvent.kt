package com.example.virginapitest.viewmodel

import com.example.virginapitest.domain.entity.Rooms

sealed class RoomsEvent {
    data class RoomLoaded(val data: List<Rooms>) :RoomsEvent()
    object RoomLoading :RoomsEvent()
    object RoomError :RoomsEvent()
    data class RoomItemClickEvent(val room: Rooms):RoomsEvent()
}
