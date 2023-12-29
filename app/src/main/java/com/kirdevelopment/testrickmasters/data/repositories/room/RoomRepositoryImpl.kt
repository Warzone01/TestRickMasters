package com.kirdevelopment.testrickmasters.data.repositories.room

import com.kirdevelopment.testrickmasters.data.local.room.RoomDatabaseOperations
import com.kirdevelopment.testrickmasters.domain.repositories.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val operations: RoomDatabaseOperations
): RoomRepository {
    override fun addRoom(roomName: String): Flow<RoomDataStatus> = flow {
        operations.insertRoom(roomName)
        val rooms = operations.retrieveRooms()
        emit(RoomDataStatus.Result(rooms))
    }.flowOn(Dispatchers.IO)

    override fun getRooms(): Flow<RoomDataStatus> = flow {
        val rooms = operations.retrieveRooms()
        emit(RoomDataStatus.Result(rooms))
    }.flowOn(Dispatchers.IO)
}