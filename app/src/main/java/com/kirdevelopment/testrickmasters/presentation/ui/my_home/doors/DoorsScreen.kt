package com.kirdevelopment.testrickmasters.presentation.ui.my_home.doors

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kirdevelopment.testrickmasters.data.repositories.door.DoorDataStatus
import com.kirdevelopment.testrickmasters.presentation.ui.my_home.doors.components.DoorItem
import com.kirdevelopment.testrickmasters.presentation.ui.my_home.doors.components.DoorItemImaged


@Composable
fun DoorsScreen(
    viewModel: DoorsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val doorDataStatus by viewModel.doorDataStatus.observeAsState()

    LaunchedEffect(state.isLoading) {
        viewModel.getDoorRealm()
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = state.isLoading),
        onRefresh = {
            viewModel.refresh()
        },
    ) {
        LazyColumn(Modifier.fillMaxSize()) {
            doorDataStatus?.let { status ->

                if (status is DoorDataStatus.Result) {
                    items(status.doorList) { door ->
                        if (door.snapshot.isNullOrBlank()) {
                            DoorItem(
                                door = door,
                                onClickFavourite = {
                                    viewModel.changeFavouriteDoor(
                                        doorId = door.id.toString(),
                                        isFavourite = !door.favorites
                                    )
                                },
                                onClickBlock = {
                                    viewModel.changeDoorBlock(
                                        doorId = door.id.toString(),
                                        isBlocked = it
                                    )
                                },
                                onClickEdit = {
                                    viewModel.changeDoorName(
                                        doorId = door.id.toString(),
                                        doorName = it
                                    )
                                }
                            )
                        } else {
                            DoorItemImaged(
                                door = door,
                                onClickFavourite = {
                                    viewModel.changeFavouriteDoor(
                                        door.id.toString(),
                                        !door.favorites
                                    )
                                },
                                onClickEdit = { viewModel.changeDoorName(
                                    doorId = door.id.toString(),
                                    doorName = it
                                ) },
                                onClickBlock =  {
                                    viewModel.changeDoorBlock(
                                        doorId = door.id.toString(),
                                        isBlocked = it
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}