package com.kirdevelopment.testrickmasters.data.repositories

import com.kirdevelopment.testrickmasters.data.remote.RickMasterApi
import com.kirdevelopment.testrickmasters.data.remote.dto.CamerasDto
import com.kirdevelopment.testrickmasters.data.remote.dto.DoorsDto
import com.kirdevelopment.testrickmasters.domain.repositories.RickMasterRepository
import javax.inject.Inject

class RickMasterRepositoryImp @Inject constructor(
    private val api: RickMasterApi
): RickMasterRepository {

    override suspend fun getDoors(): DoorsDto {
        return api.getDoors()
    }

    override suspend fun getCameras(): CamerasDto {
        return api.getCameras()
    }
}