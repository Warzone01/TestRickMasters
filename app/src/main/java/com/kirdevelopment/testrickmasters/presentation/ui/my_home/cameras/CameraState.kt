package com.kirdevelopment.testrickmasters.presentation.ui.my_home.cameras

import com.kirdevelopment.testrickmasters.data.remote.dto.Camera

data class CameraState(
    val isLoading: Boolean = false,
    val cameras: List<Camera> = emptyList(),
    val rooms: List<String> = emptyList(),
    val error: String = ""
)