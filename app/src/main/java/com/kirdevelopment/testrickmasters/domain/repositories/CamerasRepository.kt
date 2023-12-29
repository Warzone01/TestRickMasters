package com.kirdevelopment.testrickmasters.domain.repositories

import com.kirdevelopment.testrickmasters.data.repositories.camera.CameraDataStatus
import kotlinx.coroutines.flow.Flow

interface CamerasRepository {
    fun addCamera( id: String,
                   name: String,
                   favourite: Boolean,
                   rec: Boolean,
                   room: String?,
                   snapshot: String): Flow<CameraDataStatus>

    fun getCameras(): Flow<CameraDataStatus>

    fun changeFavouriteStatusCamera(cameraId: String, isFavourite: Boolean): Flow<CameraDataStatus>
}