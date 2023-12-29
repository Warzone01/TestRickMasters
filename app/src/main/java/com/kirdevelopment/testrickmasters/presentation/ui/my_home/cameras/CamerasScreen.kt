package com.kirdevelopment.testrickmasters.presentation.ui.my_home.cameras

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kirdevelopment.testrickmasters.data.repositories.camera.CameraDataStatus
import com.kirdevelopment.testrickmasters.data.repositories.room.RoomDataStatus
import com.kirdevelopment.testrickmasters.presentation.ui.my_home.cameras.components.CameraItem


@Composable
@ExperimentalMaterial3Api
fun CamerasScreen(
    viewModel: CamerasViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val cameraDataStatus by viewModel.cameraDataStatus.observeAsState()
    val roomDataStatus by viewModel.roomDataStatus.observeAsState()

    LaunchedEffect(state.isLoading) {
        viewModel.getCamerasRealm()
        viewModel.getRoomsRealm()
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
        onRefresh = {
            viewModel.refresh()
        },
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            roomDataStatus?.let { status ->
                if (status is RoomDataStatus.Result) {
                    status.roomList.forEach { room ->
                        val camerasInRoom = cameraDataStatus?.let { camStatus ->
                            if (camStatus is CameraDataStatus.Result) {
                                camStatus.cameraList.filter { camera -> camera.room == room }
                            } else emptyList()
                        }.orEmpty()

                        if (camerasInRoom.isNotEmpty()) {
                            item {
                                Text(
                                    modifier = Modifier.padding(top = 16.dp, start = 21.dp),
                                    text = room,
                                    fontSize = 21.sp,
                                )
                            }
                            camerasInRoom.forEach { camera ->
                                item {
                                    CameraItem(camera = camera, onClick = {
                                        viewModel.changeFavouriteCamera(
                                            camera.id.toString(),
                                            !camera.favorites
                                        )
                                    })
                                }
                            }
                        }
                    }
                }

                cameraDataStatus?.let { cameraStatus ->
                    if (cameraStatus is CameraDataStatus.Result && status is RoomDataStatus.Result) {
                        val unassignedCameras = cameraStatus.cameraList.filter { camera ->
                            !status.roomList.contains(camera.room)
                        }
                        items(unassignedCameras) { camera ->
                            CameraItem(camera = camera, onClick = {
                                viewModel.changeFavouriteCamera(
                                    camera.id.toString(),
                                    !camera.favorites
                                )
                            })
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.padding(bottom = 11.dp))
            }
        }
    }
}