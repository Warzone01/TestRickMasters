package com.kirdevelopment.testrickmasters.data.remote

import com.kirdevelopment.testrickmasters.data.remote.dto.CamerasDto
import com.kirdevelopment.testrickmasters.data.remote.dto.DoorsDto
import retrofit2.http.GET

interface RickMasterApi {

    @GET("/api/rubetek/doors/")
    suspend fun getDoors(): DoorsDto

    @GET("/api/rubetek/cameras/")
    suspend fun getCameras(): CamerasDto
}