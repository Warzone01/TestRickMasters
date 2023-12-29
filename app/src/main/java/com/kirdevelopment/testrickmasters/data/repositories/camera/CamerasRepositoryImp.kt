package com.kirdevelopment.testrickmasters.data.repositories.camera

import com.kirdevelopment.testrickmasters.data.local.camera.CameraDatabaseOperations
import com.kirdevelopment.testrickmasters.domain.repositories.CamerasRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CamerasRepositoryImp @Inject constructor(
    private val operations: CameraDatabaseOperations
) : CamerasRepository {
    override fun addCamera(
        id: String,
        name: String,
        favourite: Boolean,
        rec: Boolean,
        room: String?,
        snapshot: String
    ): Flow<CameraDataStatus> = flow {
        operations.insertCamera(
            id = id,
            name = name,
            favourite = favourite,
            rec = rec,
            room = room,
            snapshot = snapshot
        )
        val cameras = operations.retrieveCamera()
        emit(CameraDataStatus.Result(cameras))
    }.flowOn(Dispatchers.IO)

    override fun getCameras(): Flow<CameraDataStatus> = flow {
        val cameras = operations.retrieveCamera()
        emit(CameraDataStatus.Result(cameras))
    }.flowOn(Dispatchers.IO)

    override fun changeFavouriteStatusCamera(
        cameraId: String,
        isFavourite: Boolean
    ): Flow<CameraDataStatus> = flow {
        operations.changeCameraFavouriteStatus(cameraId, isFavourite)
        val cameras = operations.retrieveCamera()
        emit(CameraDataStatus.Result(cameras))
    }.flowOn(Dispatchers.IO)
}