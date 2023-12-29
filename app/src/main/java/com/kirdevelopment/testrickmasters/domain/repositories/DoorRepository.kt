package com.kirdevelopment.testrickmasters.domain.repositories

import com.kirdevelopment.testrickmasters.data.repositories.door.DoorDataStatus
import kotlinx.coroutines.flow.Flow

interface DoorRepository {

    fun addDoor(
        id: String,
        name: String,
        favourite: Boolean,
        room: String?,
        snapshot: String,
        isBlocked: Boolean
    ): Flow<DoorDataStatus>

    fun getDoors(): Flow<DoorDataStatus>

    fun changeFavouriteStatusDoor(doorId: String, isFavourite: Boolean): Flow<DoorDataStatus>

    fun changeDoorName(doorId: String, name: String): Flow<DoorDataStatus>

    fun changeDoorBlock(doorId: String, isBlocked: Boolean): Flow<DoorDataStatus>
}