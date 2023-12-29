package com.kirdevelopment.testrickmasters.presentation.ui.my_home.doors

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirdevelopment.testrickmasters.common.Resource
import com.kirdevelopment.testrickmasters.data.repositories.door.DoorDataStatus
import com.kirdevelopment.testrickmasters.domain.repositories.DoorRepository
import com.kirdevelopment.testrickmasters.domain.usecases.get_doors.GetDoorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoorsViewModel @Inject constructor(
    private val getDoorUseCase: GetDoorUseCase,
    private val doorsRepository: DoorRepository
): ViewModel() {

    private val _state = mutableStateOf(DoorsState())
    val state: State<DoorsState> = _state

    private val _doorDataStatus = MutableLiveData<DoorDataStatus>()
    val doorDataStatus: LiveData<DoorDataStatus>
        get() {
            return _doorDataStatus
        }

    init {
        getDoors()
    }

    // перезагрузить данные с сервера
    fun refresh() {
        _state.value = DoorsState(isLoading = true)
        getDoors()
        _state.value = DoorsState(isLoading = false)
    }

    // добавить камеру в базу данных
    fun addDoor(
        id: String,
        name: String,
        favourite: Boolean,
        room: String?,
        snapshot: String,
        isBlocked: Boolean
    ) {
        viewModelScope.launch {
            doorsRepository.addDoor(
                id = id,
                name = name,
                favourite = favourite,
                room = room,
                snapshot = snapshot,
                isBlocked = isBlocked
            ).collect {
                _doorDataStatus.value = it
            }
        }
    }

    // получить список дверей из базы данных
    fun getDoorRealm() {
        viewModelScope.launch {
            doorsRepository.getDoors().collect {
                _doorDataStatus.value = it
            }
        }
    }

    // добавить/удалить дверь из избранного
    fun changeFavouriteDoor(doorId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            doorsRepository.changeFavouriteStatusDoor(doorId, isFavourite).collect {
                _doorDataStatus.value = it
            }
        }
    }

    // изменить название двери в базе
    fun changeDoorName(doorId: String, doorName: String) {
        viewModelScope.launch {
            doorsRepository.changeDoorName(doorId, doorName).collect {
                _doorDataStatus.value = it
            }
        }
    }

    // изменить блокировку двери в базе
    fun changeDoorBlock(doorId: String, isBlocked: Boolean) {
        viewModelScope.launch {
            doorsRepository.changeDoorBlock(doorId, isBlocked).collect {
                _doorDataStatus.value = it
            }
        }
    }

    // получение списка дверей с сервера
    private fun getDoors() {
        getDoorUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DoorsState(doors = result.data?.data ?: emptyList())
                    state.value.doors.forEach { door ->
                        addDoor(
                            id = door.id.toString(),
                            name = door.name,
                            favourite = door.favorites,
                            room = door.room,
                            snapshot = door.snapshot ?: "",
                            isBlocked = false
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = DoorsState(error = result.message ?: "Неизвестная ошибка")
                }

                is Resource.Loading -> {
                    _state.value = DoorsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}