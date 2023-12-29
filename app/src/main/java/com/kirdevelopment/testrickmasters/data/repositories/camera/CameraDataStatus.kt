package com.kirdevelopment.testrickmasters.data.repositories.camera

import com.kirdevelopment.testrickmasters.data.remote.dto.Camera

sealed class CameraDataStatus {
    object Loading : CameraDataStatus()
    object Added : CameraDataStatus()
    object Changed : CameraDataStatus()
    data class Result(val cameraList: List<Camera>) : CameraDataStatus()
}