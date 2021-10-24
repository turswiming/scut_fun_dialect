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
            // 打开文件选择器
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            // 指定只显示图片
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }
    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
        fromAlbum -> {
            if (resultCode == Activity.RESULT_OK && data != null) {
                data.data?.let { uri ->
                    // 将选择的图片显示
                    val bitmap = getBitmapFromUri(uri)
                    imageView.setImageBitmap(bitmap)
                }
            }
        }

        takePhoto -> {
            if (resultCode == Activity.RESULT_OK) {
                // 将拍摄的照片显示出来
                val bitmap = BitmapFactory.decodeStream(contentResolver.
                openInputStream(imageUri))
                imageView.setImageBitmap(rotateIfRequired(bitmap))
            }
        }
    }
}
    private fun getBitmapFromUri(uri: Uri) = contentResolver
        .openFileDescriptor(uri, "r")?.use {
            BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
        }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
    val exif = ExifInterface(outputImage.path)
    val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL)
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
        else -> bitmap
    }
}
private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height,
        matrix, true)
    bitmap.recycle() // 将不再需要的Bitmap对象回收
    return rotatedBitmap
}
}


