package com.scut.fundialect.activity.publicCompose

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.halilibo.composevideoplayer.*

@Composable
fun VideScreen (uri:String){
    val context = LocalContext.current
    val exoPlayer = SimpleExoPlayer.Builder(context)
        .build()
        .apply {
            playWhenReady = false
        }
    //uri可以时网络url资源，这里我adb push了一个视频到使用sd卡根目录
    //val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://"+ context.packageName +"/"+ R.raw.video1))
    val mediaItem = MediaItem.fromUri(Uri.parse(uri))

    exoPlayer.setMediaItem(mediaItem)
    exoPlayer.prepare()
    exoPlayer.play()
    MyPlayerSurface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){
        it.player = exoPlayer
    }
}

@Composable
fun MyPlayerSurface(
    modifier: Modifier,
    onPlayerViewAvailable: (PlayerView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                useController = false
                onPlayerViewAvailable(this)
            }
        },
        modifier = modifier
    )
}


@OptIn(ExperimentalUnsignedTypes::class)
@Composable
fun VideoPlayer(
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
        videoPlayerController.hideControls()
        videoPlayerController.enableGestures(gesturesEnabled)
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
            PlayerSurface(modifier = Modifier.fillMaxSize()) {
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
@Composable
fun PlayerSurface(
    modifier: Modifier = Modifier,
    onPlayerViewAvailable: (PlayerView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                useController = false
                onPlayerViewAvailable(this)
            }
        },
        modifier = modifier
    )
}
