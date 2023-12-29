package com.kirdevelopment.testrickmasters.domain.repositories

import com.kirdevelopment.testrickmasters.data.remote.dto.CamerasDto
import com.kirdevelopment.testrickmasters.data.remote.dto.DoorsDto

interface RickMasterRepository {
    suspend fun getDoors(): DoorsDto
    suspend fun getCameras(): CamerasDto
}