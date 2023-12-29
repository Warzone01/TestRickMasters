package com.kirdevelopment.testrickmasters.data.remote.dto

data class CamerasDto(
    val data: CamerasData,
    val success: Boolean
)

data class CamerasData(
    val cameras: List<Camera>,
    val room: List<String>
)

data class Camera(
    val favorites: Boolean,
    val id: Int,
    val name: String,
    val rec: Boolean,
    val room: String ,
    val snapshot: String
)