package com.kirdevelopment.testrickmasters.domain.usecases.get_doors

import retrofit2.HttpException
import com.kirdevelopment.testrickmasters.common.Resource
import com.kirdevelopment.testrickmasters.data.remote.dto.DoorsDto
import com.kirdevelopment.testrickmasters.domain.repositories.RickMasterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetDoorUseCase @Inject constructor(
    private val repository: RickMasterRepository
) {

    operator fun invoke(): Flow<Resource<DoorsDto>> = flow {
        try {
            emit(Resource.Loading())
            val doors = repository.getDoors()
            emit(Resource.Success(doors))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Неизвестная ошибка"))
        }  catch (e: IOException) {
            emit(Resource.Error("Нет соединения с свервером. Проверьте подключение к интернету"))
        }
    }
}