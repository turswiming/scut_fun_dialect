package com.scut.fundialect.activity.publicCompose

import com.halilibo.composevideoplayer.VideoPlayerState
import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerController {

    fun setSource(source: VideoPlayerSource)

    fun play()

    fun pause()

    fun playPauseToggle()

    fun quickSeekForward()

    fun quickSeekRewind()

    fun seekTo(position: Long)

    fun reset()

    val state: StateFlow<VideoPlayerState>
}