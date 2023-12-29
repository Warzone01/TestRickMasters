package com.kirdevelopment.testrickmasters.data.repositories.room

sealed class RoomDataStatus {
    object Loading : RoomDataStatus()
    object Added : RoomDataStatus()
    object Changed : RoomDataStatus()
    data class Result(val roomList: List<String>) : RoomDataStatus()
}