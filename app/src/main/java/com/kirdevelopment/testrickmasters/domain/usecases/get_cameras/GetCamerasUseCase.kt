package com.kirdevelopment.testrickmasters.domain.usecases.get_cameras

import com.kirdevelopment.testrickmasters.common.Resource
import com.kirdevelopment.testrickmasters.data.remote.dto.CamerasDto
import com.kirdevelopment.testrickmasters.domain.repositories.RickMasterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCamerasUseCase @Inject constructor(
    private val repository: RickMasterRepository
) {

    operator fun invoke(): Flow<Resource<CamerasDto>> = flow {
        try {
            emit(Resource.Loading())
            val cameras = repository.getCameras()
            emit(Resource.Success(cameras))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Неизвестная ошибка"))
        }  catch (e: IOException) {
            emit(Resource.Error("Нет соединения с свервером. Проверьте подключение к интернету"))
        }
    }
}