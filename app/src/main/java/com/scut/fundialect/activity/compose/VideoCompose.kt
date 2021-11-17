package com.scut.fundialect.activity.compose

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.scut.fundialect.R
import com.scut.fundialect.ui.theme.black

class VideoCompose {



}
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
    PlayerSurface(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ){
        it.player = exoPlayer
    }
}

@Composable
fun PlayerSurface(
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
