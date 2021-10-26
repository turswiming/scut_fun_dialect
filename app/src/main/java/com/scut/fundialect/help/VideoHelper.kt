package com.scut.fundialect.help

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.scut.fundialect.R
import java.io.File
import java.net.URI

object VideoHelper {
    val takePhoto = 1
    val fromAlbum = 2
    lateinit var videoUri: Uri
    lateinit var outputVideo: File
    public fun gitVideoFromCamera(context: Context){
        val activity:AppCompatActivity = context as AppCompatActivity
        // 创建File对象，用于存储拍照后的图片
        outputVideo = File(activity.externalCacheDir, "output_video.jpg")
        if (outputVideo.exists()) {
            outputVideo.delete()
        }
        outputVideo.createNewFile()
        videoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, "com.scut.fundialect.fileprovider", outputVideo)
        } else {
            Uri.fromFile(outputVideo)
        }
        // 启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)

        activity.startActivityForResult(intent, takePhoto)
    }


    public fun gitVideoFromAlbum(fromAlbum: Int,context: Context) {

        // 打开文件选择器
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 指定只显示图片
        intent.type = "video/*"

        //把context强制转换成activity
        //然后执行调用此类的activity内的函数
        val mActivity :AppCompatActivity = context as AppCompatActivity
        mActivity.startActivityForResult(intent, fromAlbum)

    }
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
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    // 将拍摄的照片显示出来
                    var bitmap = BitmapFactory.decodeStream(contentResolver.
                    openInputStream(videoUri))
                    bitmap = rotateIfRequired(bitmap)
                }
            }


        }

        return defaultUri()

    }

    private fun defaultUri() =
        Uri.parse("android.resource://" + "com.scut.fundialect" + "/" + R.raw.defaultpic)

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
    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputVideo.path)
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
