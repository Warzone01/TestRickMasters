package com.kirdevelopment.testrickmasters.data.repositories.door

import com.kirdevelopment.testrickmasters.data.local.door.DoorDatabaseOperations
import com.kirdevelopment.testrickmasters.domain.repositories.DoorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DoorRepositoryImp @Inject constructor(
    private val operations: DoorDatabaseOperations
): DoorRepository {

    override fun addDoor(
        id: String,
        name: String,
        favourite: Boolean,
        room: String?,
        snapshot: String,
        isBlocked: Boolean
    ): Flow<DoorDataStatus> = flow {
        operations.insertDoor(
            id = id,
            name = name,
            favourite = favourite,
            room = room,
            snapshot = snapshot,
            isBlocked = isBlocked
        )
        val doors = operations.retrieveDoor()
        emit(DoorDataStatus.Result(doors))
    }.flowOn(Dispatchers.IO)

    override fun getDoors(): Flow<DoorDataStatus> = flow {
        val doors = operations.retrieveDoor()
        emit(DoorDataStatus.Result(doors))
    }.flowOn(Dispatchers.IO)

    override fun changeFavouriteStatusDoor(
        doorId: String,
        isFavourite: Boolean
    ): Flow<DoorDataStatus> = flow {
        operations.changeDoorFavouriteStatus(doorId, isFavourite)
        val doors = operations.retrieveDoor()
        emit(DoorDataStatus.Result(doors))
    }.flowOn(Dispatchers.IO)

    override fun changeDoorName(doorId: String, name: String): Flow<DoorDataStatus> = flow {
        operations.editDoorName(doorId, name)
        val doors = operations.retrieveDoor()
        emit(DoorDataStatus.Result(doors))
    }.flowOn(Dispatchers.IO)

    override fun changeDoorBlock(doorId: String, isBlocked: Boolean): Flow<DoorDataStatus> = flow {
        operations.editDoorBlocked(doorId, isBlocked)
        val doors = operations.retrieveDoor()
        emit(DoorDataStatus.Result(doors))
    }.flowOn(Dispatchers.IO)
}