package com.kirdevelopment.testrickmasters.domain.repositories

import com.kirdevelopment.testrickmasters.data.repositories.room.RoomDataStatus
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun addRoom(
        roomName: String
    ): Flow<RoomDataStatus>

    fun getRooms(): Flow<RoomDataStatus>
}