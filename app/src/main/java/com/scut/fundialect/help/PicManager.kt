package com.scut.fundialect.help

import android.R.attr
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.scut.fundialect.MainActivity
import com.scut.fundialect.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr.path
import android.content.Context
import androidx.core.net.toUri


object PicManager {
    val takePhoto = 1
    val fromAlbum = 2
    public fun getPicUri(
        requestCode: Int, resultCode: Int, data: Intent?,
        contentResolver: ContentResolver
    ): Uri {
        when (requestCode) {
            fromAlbum -> {
                if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                    data.data?.let {
                        //这玩意儿就是我们梦寐以求的uri
                            uri ->
                        // 将选择的图片显示
                        val bitmap = getBitmapFromUri(uri, contentResolver)
                        return uri
                    }
                }
            }

        }

        return defaultUri()

    }

    private fun defaultUri() =
        Uri.parse("android.resource://" + "com.scut.fundialect" + "/" + R.raw.defaultPic)

    private fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver) {
        return contentResolver.openFileDescriptor(uri, "r").use {
            BitmapFactory.decodeFileDescriptor(it?.fileDescriptor)
        }
    }

    public fun CopyPic(outuri: Uri,context:Context): Uri {
        //为啥这要封装一层？
        //您猜，哎嘿
        return copyFile(outuri,context)
    }

    fun copyFile(uri: Uri,context:Context):Uri {
        lateinit var file: File
        val fromFile =  File(URI(uri.toString()));
        fromFile.runCatching {
            takeIf {
                it.exists()
            }?.inputStream()?.use {
                    inputStream ->
                file = context.getFilesDir()
                file =File(file.path,"name")
                file.outputStream().use {
                        outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }.onFailure {
            return defaultUri()
        }
        return file.toUri()
    }
}


