package com.scut.fundialect.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scut.fundialect.R


class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
//        val videoView = findViewById<VideoView>(R.id.videoView)
//        videoView.setVideoURI(Uri.parse("android.resource://"+ packageName +"/"+R.raw.video1)) // 香港卫视地址
//
//        videoView.start()
////使视频能够暂停、播放、进度条显示等控制
////使视频能够暂停、播放、进度条显示等控制
//        val mediaController = MediaController(this)
//        videoView.setMediaController(mediaController)
//        mediaController.setMediaPlayer(videoView)
    }
}