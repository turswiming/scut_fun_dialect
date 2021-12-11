package com.scut.fundialect.activity.video

import android.os.Parcelable
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.scut.fundialect.activity.video.util.getDurationString
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.abs

@Composable
fun MediaControlGestures(
    modifier: Modifier = Modifier
) {
    val controller = LocalVideoPlayerController.current

    val controlsEnabled by controller.collect { controlsEnabled }
    val gesturesEnabled by controller.collect { gesturesEnabled }
    val controlsVisible by controller.collect { controlsVisible }
    val quickSeekDirection by controller.collect { quickSeekAction.direction }
    val draggingProgress by controller.collect { draggingProgress }

    if (controlsEnabled && !controlsVisible && gesturesEnabled) {
        Box(
            modifier = modifier
                .draggingProgressOverlay(draggingProgress)
                .quickSeekAnimation(quickSeekDirection) {
                    controller.setQuickSeekAction(QuickSeekAction.none())
                }) {
            GestureBox()
        }
    }

}

@Composable
fun GestureBox(modifier: Modifier = Modifier) {
    val controller = LocalVideoPlayerController.current

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(controller) {
            var wasPlaying = true
            var totalOffset = Offset.Zero
            var diffTime = -1f

            var duration: Long = 0
            var currentPosition: Long = 0

            // When this job completes, it seeks to desired position.
            // It gets cancelled if delay does not complete
            var seekJob: Job? = null

            fun resetState() {
                totalOffset = Offset.Zero
                controller.setDraggingProgress(null)
            }

            detectMediaPlayerGesture(
                onDoubleTap = { doubleTapPosition ->
                    when {
                        doubleTapPosition.x < size.width * 0.4f -> {
                            controller.quickSeekRewind()
                        }
                        doubleTapPosition.x > size.width * 0.6f -> {
                            controller.quickSeekForward()
                        }
                    }
                },
                onTap = {
                    controller.showControls()
                },
                onDragStart = { offset ->
                    wasPlaying = controller.currentState { it.isPlaying }
                    controller.pause()

                    currentPosition = controller.currentState { it.currentPosition }
                    duration = controller.currentState { it.duration }

                    resetState()
                },
                onDragEnd = {
                    if (wasPlaying) controller.play()
                    resetState()
                },
                onDrag = { dragAmount: Float ->
                    seekJob?.cancel()

                    totalOffset += Offset(x = dragAmount, y = 0f)

                    val diff = totalOffset.x

                    diffTime = if (duration <= 60_000) {
                        duration.toFloat() * diff / size.width.toFloat()
                    } else {
                        60_000.toFloat() * diff / size.width.toFloat()
                    }

                    var finalTime = currentPosition + diffTime
                    if (finalTime < 0) {
                        finalTime = 0f
                    } else if (finalTime > duration) {
                        finalTime = duration.toFloat()
                    }
                    diffTime = finalTime - currentPosition

                    controller.setDraggingProgress(
                        DraggingProgress(
                            finalTime = finalTime,
                            diffTime = diffTime
                        )
                    )

                    seekJob = coroutineScope.launch {
                        delay(200)
                        controller.seekTo(finalTime.toLong())
                    }
                }
            )
        }
        .then(modifier)
    )
}

suspend fun PointerInputScope.detectMediaPlayerGesture(
    onTap: (Offset) -> Unit,
    onDoubleTap: (Offset) -> Unit,
    onDragStart: (Offset) -> Unit,
    onDragEnd: () -> Unit,
    onDrag: (Float) -> Unit
) {
    coroutineScope {
        launch {
            detectHorizontalDragGestures(
                onDragStart = onDragStart,
                onDragEnd = onDragEnd,
                onHorizontalDrag = { change, dragAmount ->
                    onDrag(dragAmount)
                    change.consumePositionChange()
                },
            )
        }

        launch {
            detectTapGestures(
                onTap = onTap,
                onDoubleTap = onDoubleTap
            )
        }
    }
}

fun Modifier.quickSeekAnimation(
    quickSeekDirection: QuickSeekDirection,
    onAnimationEnd: () -> Unit
) = composed {
    val alphaRewind = remember { Animatable(0f) }
    val alphaForward = remember { Animatable(0f) }

    LaunchedEffect(quickSeekDirection) {
        when (quickSeekDirection) {
            QuickSeekDirection.Rewind -> alphaRewind
            QuickSeekDirection.Forward -> alphaForward
            else -> null
        }?.let { animatable ->
            animatable.animateTo(1f)
            animatable.animateTo(0f)
            onAnimationEnd()
        }
    }

    Row(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            ShadowedIcon(
                Icons.Filled.FastRewind,
                modifier = Modifier
                    .alpha(alphaRewind.value)
                    .align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            ShadowedIcon(
                Icons.Filled.FastForward,
                modifier = Modifier
                    .alpha(alphaForward.value)
                    .align(Alignment.Center)
            )
        }
    }

    this
}

fun Modifier.draggingProgressOverlay(draggingProgress: DraggingProgress?) = composed {
    if (draggingProgress != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                draggingProgress.progressText,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    shadow = Shadow(
                        blurRadius = 8f,
                        offset = Offset(2f, 2f)
                    )
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    this
}

@Parcelize
data class DraggingProgress(
    val finalTime: Float,
    val diffTime: Float
) : Parcelable {
    val progressText: String
        get() = "${getDurationString(finalTime.toLong(), false)} " +
            "[${if (diffTime < 0) "-" else "+"}${
                getDurationString(
                    abs(diffTime.toLong()),
                    false
                )
            }]"
}

enum class QuickSeekDirection {
    None,
    Rewind,
    Forward
}

@Parcelize
data class QuickSeekAction(
    val direction: QuickSeekDirection
) : Parcelable {
    // Each action is unique
    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(direction)
    }

    companion object {
        fun none() = QuickSeekAction(QuickSeekDirection.None)
        fun forward() = QuickSeekAction(QuickSeekDirection.Forward)
        fun rewind() = QuickSeekAction(QuickSeekDirection.Rewind)
    }
}

public val Icons.Filled.FastRewind: ImageVector
    get() {
        if (_fastRewind != null) {
            return _fastRewind!!
        }
        _fastRewind = materialIcon(name = "Filled.FastRewind") {
            materialPath {
                moveTo(11.0f, 18.0f)
                lineTo(11.0f, 6.0f)
                lineToRelative(-8.5f, 6.0f)
                lineToRelative(8.5f, 6.0f)
                close()
                moveTo(11.5f, 12.0f)
                lineToRelative(8.5f, 6.0f)
                lineTo(20.0f, 6.0f)
                lineToRelative(-8.5f, 6.0f)
                close()
            }
        }
        return _fastRewind!!
    }

private var _fastRewind: ImageVector? = null

public val Icons.Filled.FastForward: ImageVector
    get() {
        if (_fastForward != null) {
            return _fastForward!!
        }
        _fastForward = materialIcon(name = "Filled.FastForward") {
            materialPath {
                moveTo(4.0f, 18.0f)
                lineToRelative(8.5f, -6.0f)
                lineTo(4.0f, 6.0f)
                verticalLineToRelative(12.0f)
                close()
                moveTo(13.0f, 6.0f)
                verticalLineToRelative(12.0f)
                lineToRelative(8.5f, -6.0f)
                lineTo(13.0f, 6.0f)
                close()
            }
        }
        return _fastForward!!
    }

private var _fastForward: ImageVector? = null
