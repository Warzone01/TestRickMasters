package com.kirdevelopment.testrickmasters.presentation.ui.my_home.cameras

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirdevelopment.testrickmasters.common.Resource
import com.kirdevelopment.testrickmasters.data.repositories.camera.CameraDataStatus
import com.kirdevelopment.testrickmasters.data.repositories.room.RoomDataStatus
import com.kirdevelopment.testrickmasters.domain.repositories.CamerasRepository
import com.kirdevelopment.testrickmasters.domain.repositories.RoomRepository
import com.kirdevelopment.testrickmasters.domain.usecases.get_cameras.GetCamerasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CamerasViewModel @Inject constructor(
    private val getCamerasUseCase: GetCamerasUseCase,
    private val camerasRepository: CamerasRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    private val _state = mutableStateOf(CameraState())
    val state: State<CameraState> = _state

    private val _cameraDataStatus = MutableLiveData<CameraDataStatus>()
    val cameraDataStatus: LiveData<CameraDataStatus>
        get() {
            return _cameraDataStatus
        }

    private val _roomDataStatus = MutableLiveData<RoomDataStatus>()
    val roomDataStatus: LiveData<RoomDataStatus>
        get() {
            return _roomDataStatus
        }

    init {
        getCameras()
    }

    // обновить список камер
    fun refresh() {
        _state.value = CameraState(isLoading = true)
        getCameras()
        _state.value = CameraState(isLoading = false)
    }

    // добавить камеру в базу данных
    fun addCamera(
        id: String,
        name: String,
        favourite: Boolean,
        rec: Boolean,
        room: String?,
        snapshot: String
    ) {
        viewModelScope.launch {
            camerasRepository.addCamera(id, name, favourite, rec, room, snapshot).collect {
                _cameraDataStatus.value = it
            }
        }
    }

    // добавляет в базу данных полученные комнаты
    fun addRoom(
        roomName: String
    ) {
        viewModelScope.launch {
            roomRepository.addRoom(roomName).collect {
                _roomDataStatus.value = it
            }
        }
    }

    // получить список комнат из базы данных
    fun getRoomsRealm() {
        viewModelScope.launch {
            roomRepository.getRooms().collect {
                _roomDataStatus.value = it
            }
        }
    }

    // получить список камер из базы данных
    fun getCamerasRealm() {
        viewModelScope.launch {
            camerasRepository.getCameras().collect {
                _cameraDataStatus.value = it
            }
        }
    }

    // добавить/удалить камеру из избранного
    fun changeFavouriteCamera(cameraId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            camerasRepository.changeFavouriteStatusCamera(cameraId, isFavourite).collect {
                _cameraDataStatus.value = it
            }
        }
    }

    // получение камеры с сервера
    private fun getCameras() {
        getCamerasUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CameraState(
                        cameras = result.data?.data?.cameras ?: emptyList(),
                        rooms = result.data?.data?.room ?: emptyList()
                    )
                    state.value.rooms.forEach { room ->
                        addRoom(room)
                    }
                    state.value.cameras.forEach { camera ->
                        addCamera(
                            id = camera.id.toString(),
                            name = camera.name,
                            favourite = camera.favorites,
                            rec = camera.favorites,
                            room = camera.room,
                            snapshot = camera.snapshot
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = CameraState(error = result.message ?: "Неизвестная ошибка")
                }

                is Resource.Loading -> {
                    _state.value = CameraState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}