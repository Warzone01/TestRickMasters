package com.kirdevelopment.testrickmasters.presentation.ui.my_home.doors

import com.kirdevelopment.testrickmasters.data.remote.dto.Door

data class DoorsState(
    val isLoading: Boolean = false,
    val doors: List<Door> = emptyList(),
    val error: String = ""
)