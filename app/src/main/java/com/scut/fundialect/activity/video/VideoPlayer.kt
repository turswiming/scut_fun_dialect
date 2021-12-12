package com.scut.fundialect.activity.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

internal val LocalVideoPlayerController =
    compositionLocalOf<DefaultVideoPlayerController> { error("VideoPlayerController is not initialized") }

@Composable
fun rememberVideoPlayerController(
    source: VideoPlayerSource? = null
): VideoPlayerController {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    return rememberSaveable(
        context, coroutineScope,
        saver = object : Saver<DefaultVideoPlayerController, VideoPlayerState> {
            override fun restore(value: VideoPlayerState): DefaultVideoPlayerController {
                return DefaultVideoPlayerController(
                    context = context,
                    initialState = value,
                    coroutineScope = coroutineScope
                )
            }

            override fun SaverScope.save(value: DefaultVideoPlayerController): VideoPlayerState {
                return value.currentState { it }
            }
        },
        init = {
            DefaultVideoPlayerController(
                context = context,
                initialState = VideoPlayerState(),
                coroutineScope = coroutineScope
            ).apply {
                source?.let { setSource(it) }
            }
        }
    )
}

@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoPlayer2(
    videoPlayerController: VideoPlayerController,
    modifier: Modifier = Modifier,
    controlsEnabled: Boolean = true,
    gesturesEnabled: Boolean = true,
    backgroundColor: Color = Color.Black
) {
    require(videoPlayerController is DefaultVideoPlayerController) {
        "Use [rememberVideoPlayerController] to create an instance of [VideoPlayerController]"
    }

    SideEffect {
        videoPlayerController.videoPlayerBackgroundColor = backgroundColor.value.toInt()
        videoPlayerController.enableControls(controlsEnabled)
        videoPlayerController.enableGestures(gesturesEnabled)
        videoPlayerController.hideControls()

    }

    CompositionLocalProvider(
        LocalContentColor provides Color.White,
        LocalVideoPlayerController provides videoPlayerController
    ) {
        val aspectRatio by videoPlayerController.collect { videoSize.first / videoSize.second }

        Box(
            modifier = Modifier
                .background(color = backgroundColor)
                .aspectRatio(aspectRatio)
                .then(modifier)
        ) {
            PlayerSurface2 {
                videoPlayerController.playerViewAvailable(it)
            }

            MediaControlGestures(modifier = Modifier.matchParentSize())
            MediaControlButtons(modifier = Modifier.matchParentSize())
            ProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoPlayer3(
    onSeek:(Float)->Unit,
    onSeekStopped:(Float)->Unit,
    videoPlayerController: VideoPlayerController,
    modifier: Modifier = Modifier,
    controlsEnabled: Boolean = true,
    gesturesEnabled: Boolean = true,
    backgroundColor: Color = Color.Black
) {
    require(videoPlayerController is DefaultVideoPlayerController) {
        "Use [rememberVideoPlayerController] to create an instance of [VideoPlayerController]"
    }

    SideEffect {
        videoPlayerController.videoPlayerBackgroundColor = backgroundColor.value.toInt()
        videoPlayerController.enableControls(controlsEnabled)
        videoPlayerController.enableGestures(gesturesEnabled)
        videoPlayerController.hideControls()

    }

    CompositionLocalProvider(
        LocalContentColor provides Color.White,
        LocalVideoPlayerController provides videoPlayerController
    ) {
        val aspectRatio by videoPlayerController.collect { videoSize.first / videoSize.second }
        Surface(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = backgroundColor)
                    .aspectRatio(aspectRatio)
                    .then(modifier)
            ) {
                PlayerSurface2 {
                    videoPlayerController.playerViewAvailable(it)
                }
            }
            MediaControlGestures(modifier = Modifier.fillMaxSize())
            MediaControlButtons(modifier = Modifier.fillMaxSize())
            Box(
                Modifier.fillMaxSize()
                .padding(0.dp,0.dp,0.dp,5.dp)
            ) {
                ProgressIndicator3(
                    onSeek={
                        onSeek(it)
                    },
                    onSeekStopped={
                        onSeekStopped(it)

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }



        }

    }
}