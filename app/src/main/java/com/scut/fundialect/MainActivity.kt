package com.scut.fundialect


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.scut.fundialect.database.UserInfoDataBaseHelper

import com.scut.fundialect.database.VideoDataBaseHelper
import com.scut.fundialect.help.PicManager
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

        takePhotoBtn.setOnClickListener {
            gitPic(fromAlbum)
        }
   }

    private fun MainActivity.gitPic(fromAlbum: Int) {
        // 打开文件选择器
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 指定只显示图片
        intent.type = "image/*"
        startActivityForResult(intent, fromAlbum)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
        PicManager.getPicUri(requestCode, resultCode, data,contentResolver)
}





}


