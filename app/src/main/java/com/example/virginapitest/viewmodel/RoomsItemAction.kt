package com.example.virginapitest.viewmodel

import com.example.virginapitest.domain.entity.Rooms

sealed class RoomsItemAction {
    object FetchRoom:RoomsItemAction()
    data class RoomItemClicked(val room: Rooms):RoomsItemAction()
}
