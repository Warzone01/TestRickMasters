package com.kirdevelopment.testrickmasters.presentation.ui.my_home.doors.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.kirdevelopment.testrickmasters.R
import com.kirdevelopment.testrickmasters.data.remote.dto.Door
import com.kirdevelopment.testrickmasters.presentation.ui.theme.DarkGray
import com.kirdevelopment.testrickmasters.presentation.ui.theme.StrokeGray
import com.kirdevelopment.testrickmasters.presentation.ui.theme.White
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun DoorItem(
    door: Door,
    onClickFavourite: () -> Unit,
    onClickEdit: (newName: String) -> Unit,
    onClickBlock: (isBlock: Boolean) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val animatableOffsetX = remember { Animatable(0f) }
    val velocityTracker = remember { VelocityTracker() }
    val coroutineScope = rememberCoroutineScope()
    val isVisibleStateEdit = remember { MutableTransitionState(false) }
    val isVisibleStateFavourite = remember { MutableTransitionState(false) }
    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var editedName by rememberSaveable { mutableStateOf(door.name) }
    isVisibleStateEdit.targetState = offsetX < -150f
    isVisibleStateFavourite.targetState = offsetX < -250f

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .fillMaxWidth()
                .padding(end = 22.dp, start = 22.dp, top = 8.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            velocityTracker.resetTracking()
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            velocityTracker.addPosition(change.uptimeMillis, change.position)
                            val targetOffsetX = (offsetX + dragAmount).coerceIn(-300f, 0f)
                            coroutineScope.launch {
                                animatableOffsetX.snapTo(targetOffsetX)
                                offsetX = targetOffsetX
                            }
                        },
                        onDragEnd = {
                            val targetValue = if (offsetX < -50) -300f else 0f
                            coroutineScope.launch {
                                animatableOffsetX.animateTo(targetValue, spring())
                                offsetX = targetValue
                            }
                        }
                    )
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = White,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row {

                    if (isEditMode) {
                        TextField(
                            modifier = Modifier
                                .padding(vertical = 5.dp, horizontal = 16.dp)
                                .weight(1f),
                            value = editedName,
                            onValueChange = { editedName = it },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = White,
                                unfocusedIndicatorColor = White,
                                focusedContainerColor = White,
                                focusedIndicatorColor = White,
                            )
                        )
                    } else {
                        Text(
                            text = door.name,
                            fontWeight = FontWeight.Light,
                            color = DarkGray,
                            modifier = Modifier
                                .padding(vertical = 22.dp, horizontal = 16.dp)
                                .weight(1f)
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(horizontal = 13.dp),
                        onClick = {
                            if (isEditMode) {
                                onClickEdit(editedName)
                                isEditMode = false
                            } else {
                                onClickBlock(!door.isBlocked)
                            }
                        }
                    ) {
                        Image(
                            painter = painterResource(
                                id = when {
                                    isEditMode -> R.drawable.ic_save
                                    !isEditMode && door.isBlocked -> R.drawable.ic_lock
                                    else -> R.drawable.ic_unlock
                                }),
                                contentDescription = null
                            )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(end = 21.dp, start = 9.dp)
                .align(Alignment.CenterEnd)
        ) {
            AnimatedVisibility(
                modifier = Modifier.padding(end = 19.dp),
                visible = isVisibleStateFavourite.targetState,
                enter = scaleIn(),
                exit = scaleOut()
            ) {

                IconButton(
                    onClick = {
                        isEditMode = !isEditMode
                        offsetX = 0f
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .border(1.dp, StrokeGray, CircleShape)
                ) {
                    Image(
                        painter = painterResource(
                            // после обновления списка всё вернётся как было, потому, что на беке не обновляем
                            id = R.drawable.ic_edit
                        ),
                        contentDescription = "Избранное"
                    )
                }
            }

            AnimatedVisibility(
                visible = isVisibleStateEdit.targetState,
                enter = scaleIn(),
                exit = scaleOut()
            ) {

                IconButton(
                    onClick = { onClickFavourite() },
                    modifier = Modifier
                        .size(36.dp)
                        .border(1.dp, StrokeGray, CircleShape)
                ) {
                    Image(
                        painter = painterResource(
                            // после обновления списка всё вернётся как было, потому, что на беке не обновляем
                            id = if (door.favorites) R.drawable.ic_star else R.drawable.ic_empty_star
                        ),
                        contentDescription = "Избранное"
                    )
                }
            }
        }
    }
}