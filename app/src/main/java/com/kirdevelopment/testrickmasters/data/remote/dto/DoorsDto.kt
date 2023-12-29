package com.kirdevelopment.testrickmasters.data.remote.dto

data class DoorsDto(
    val data: List<Door>,
    val success: Boolean
)

data class Door(
    val favorites: Boolean,
    val id: Int,
    val name: String,
    val room: String,
    val snapshot: String?,
    val isBlocked: Boolean
)