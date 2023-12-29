package com.kirdevelopment.testrickmasters.presentation.ui.my_home.cameras.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kirdevelopment.testrickmasters.R
import com.kirdevelopment.testrickmasters.data.remote.dto.Camera
import com.kirdevelopment.testrickmasters.presentation.ui.theme.DarkGray
import com.kirdevelopment.testrickmasters.presentation.ui.theme.StrokeGray
import com.kirdevelopment.testrickmasters.presentation.ui.theme.White
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CameraItem(camera: Camera, onClick: () -> Unit) {
    var offsetX by remember { mutableStateOf(0f) }
    val animatableOffsetX = remember { Animatable(0f) }
    val velocityTracker = remember { VelocityTracker() }
    val coroutineScope = rememberCoroutineScope()
    val isVisibleStateFavourite = remember { MutableTransitionState(false) }
    isVisibleStateFavourite.targetState = offsetX < -100f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(279.dp)
    ) {
        Card(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .fillMaxWidth()
                .height(279.dp)
                .padding(end = 22.dp, start = 22.dp, top = 8.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            velocityTracker.resetTracking()
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            velocityTracker.addPosition(change.uptimeMillis, change.position)
                            val targetOffsetX = (offsetX + dragAmount).coerceIn(-150f, 0f)
                            coroutineScope.launch {
                                animatableOffsetX.snapTo(targetOffsetX)
                                offsetX = targetOffsetX
                            }
                        },
                        onDragEnd = {
                            val targetValue = if (offsetX < -50) -150f else 0f
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
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        model = camera.snapshot,
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier
                            .size(width = 60.dp, height = 60.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "Play",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    if (camera.favorites) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        )
                    }
                    if (camera.rec) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_rec),
                            contentDescription = "Favorite",
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                        )
                    }
                }

                Text(
                    text = camera.name,
                    fontWeight = FontWeight.Light,
                    color = DarkGray,
                    modifier = Modifier.padding(vertical = 22.dp, horizontal = 16.dp)
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .padding(end = 21.dp, start = 9.dp)
                .align(Alignment.CenterEnd),
            visible = isVisibleStateFavourite.targetState,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            IconButton(
                onClick = { onClick() },
                modifier = Modifier
                    .size(36.dp)
                    .border(1.dp, StrokeGray, CircleShape)
            ) {
                Image(
                    painter = painterResource(
                        // после обновления списка всё вернётся как было, потому, что на беке не обновляем
                        id = if (camera.favorites) R.drawable.ic_star else R.drawable.ic_empty_star
                    ),
                    contentDescription = "Избранное"
                )
            }
        }
    }
}