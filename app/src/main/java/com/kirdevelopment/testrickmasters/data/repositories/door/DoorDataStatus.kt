package com.kirdevelopment.testrickmasters.data.repositories.door

import com.kirdevelopment.testrickmasters.data.remote.dto.Door

sealed class DoorDataStatus {
    object Loading : DoorDataStatus()
    object Added : DoorDataStatus()
    object Changed : DoorDataStatus()
    data class Result(val doorList: List<Door>) : DoorDataStatus()
}