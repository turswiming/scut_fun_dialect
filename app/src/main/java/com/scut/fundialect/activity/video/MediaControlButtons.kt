package com.scut.fundialect.activity.video

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.scut.fundialect.activity.video.util.getDurationString

@Composable
fun MediaControlButtons(
    modifier: Modifier = Modifier
) {
    val controller = LocalVideoPlayerController.current

    val controlsEnabled by controller.collect { controlsEnabled }

    // Dictates the direction of appear animation.
    // If controlsVisible is true, appear animation needs to be triggered.
    val controlsVisible by controller.collect { controlsVisible }

    // When controls are not visible anymore we should remove them from UI tree
    // Controls by default should always be on screen.
    // Only when disappear animation finishes, controls can be freely cleared from the tree.
    val (controlsExistOnUITree, setControlsExistOnUITree) = remember(controlsVisible) {
        mutableStateOf(true)
    }

    val appearAlpha = remember { Animatable(0f) }

    LaunchedEffect(controlsVisible) {
        appearAlpha.animateTo(
            targetValue = if (controlsVisible) 1f else 0f,
            animationSpec = tween(
                durationMillis = 250,
                easing = LinearEasing
            )
        )
        setControlsExistOnUITree(controlsVisible)
    }

    if (controlsEnabled && controlsExistOnUITree) {
        MediaControlButtonsContent(
            modifier = Modifier
                .alpha(appearAlpha.value)
                .background(Color.Black.copy(alpha = appearAlpha.value * 0.6f))
                .then(modifier)
        )
    }
}

@Composable
private fun MediaControlButtonsContent(modifier: Modifier = Modifier) {
    val controller = LocalVideoPlayerController.current

    Box(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                controller.hideControls()
            })
        PositionAndDurationNumbers(modifier = Modifier.align(Alignment.BottomCenter))
        PlayPauseButton(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun PositionAndDurationNumbers(
    modifier: Modifier = Modifier
) {
    val controller = LocalVideoPlayerController.current

    val positionText by controller.collect {
        getDurationString(currentPosition, false)
    }
    val remainingDurationText by controller.collect {
        getDurationString(duration - currentPosition, false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            positionText,
            style = TextStyle(
                shadow = Shadow(
                    blurRadius = 8f,
                    offset = Offset(2f, 2f)
                )
            )
        )
        Box(modifier = Modifier.weight(1f))
        Text(
            remainingDurationText,
            style = TextStyle(
                shadow = Shadow(
                    blurRadius = 8f,
                    offset = Offset(2f, 2f)
                )
            )
        )
    }
}

@Composable
fun PlayPauseButton(modifier: Modifier = Modifier) {
    val controller = LocalVideoPlayerController.current

    val isPlaying by controller.collect { isPlaying }
    val playbackState by controller.collect { playbackState }

    IconButton(
        onClick = { controller.playPauseToggle() },
        modifier = modifier
    ) {
        if (isPlaying) {
            ShadowedIcon(icon = Icons.Filled.Pause)
        } else {
            when (playbackState) {
                PlaybackState.ENDED -> {
                    ShadowedIcon(icon = Icons.Filled.Restore)
                }
                PlaybackState.BUFFERING -> {
                    CircularProgressIndicator()
                }
                else -> {
                    ShadowedIcon(icon = Icons.Filled.PlayArrow)
                }
            }
        }
    }
}

public val Icons.Filled.Pause: ImageVector
    get() {
        if (_pause != null) {
            return _pause!!
        }
        _pause = materialIcon(name = "Filled.Pause") {
            materialPath {
                moveTo(6.0f, 19.0f)
                horizontalLineToRelative(4.0f)
                lineTo(10.0f, 5.0f)
                lineTo(6.0f, 5.0f)
                verticalLineToRelative(14.0f)
                close()
                moveTo(14.0f, 5.0f)
                verticalLineToRelative(14.0f)
                horizontalLineToRelative(4.0f)
                lineTo(18.0f, 5.0f)
                horizontalLineToRelative(-4.0f)
                close()
            }
        }
        return _pause!!
    }

private var _pause: ImageVector? = null


public val Icons.Filled.Restore: ImageVector
    get() {
        if (_restore != null) {
            return _restore!!
        }
        _restore = materialIcon(name = "Filled.Restore") {
            materialPath {
                moveTo(13.0f, 3.0f)
                curveToRelative(-4.97f, 0.0f, -9.0f, 4.03f, -9.0f, 9.0f)
                lineTo(1.0f, 12.0f)
                lineToRelative(3.89f, 3.89f)
                lineToRelative(0.07f, 0.14f)
                lineTo(9.0f, 12.0f)
                lineTo(6.0f, 12.0f)
                curveToRelative(0.0f, -3.87f, 3.13f, -7.0f, 7.0f, -7.0f)
                reflectiveCurveToRelative(7.0f, 3.13f, 7.0f, 7.0f)
                reflectiveCurveToRelative(-3.13f, 7.0f, -7.0f, 7.0f)
                curveToRelative(-1.93f, 0.0f, -3.68f, -0.79f, -4.94f, -2.06f)
                lineToRelative(-1.42f, 1.42f)
                curveTo(8.27f, 19.99f, 10.51f, 21.0f, 13.0f, 21.0f)
                curveToRelative(4.97f, 0.0f, 9.0f, -4.03f, 9.0f, -9.0f)
                reflectiveCurveToRelative(-4.03f, -9.0f, -9.0f, -9.0f)
                close()
                moveTo(12.0f, 8.0f)
                verticalLineToRelative(5.0f)
                lineToRelative(4.28f, 2.54f)
                lineToRelative(0.72f, -1.21f)
                lineToRelative(-3.5f, -2.08f)
                lineTo(13.5f, 8.0f)
                lineTo(12.0f, 8.0f)
                close()
            }
        }
        return _restore!!
    }

private var _restore: ImageVector? = null
