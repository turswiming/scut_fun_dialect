package com.scut.fundialect


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.scut.fundialect.help.PicManager
import com.scut.fundialect.help.VideoHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


data class Message(val author: String,val body:String)


class MainActivity : AppCompatActivity() {
    val takePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VideoHelper.outputVideo = File(externalCacheDir, "output_image.jpg")
        takePhotoBtn.setOnClickListener {
            PicManager.gitPic(fromAlbum,this)
        }
   }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
        PicManager.getPicUri(requestCode, resultCode, data,contentResolver)
}





}


