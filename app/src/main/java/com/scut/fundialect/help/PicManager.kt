package com.scut.fundialect.help

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.scut.fundialect.R
import java.io.File
import java.net.URI
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.transition.Transition
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.net.toUri


object PicManager {
    val takePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    public fun gitPicFromCamera(context: Context){
        val activity:AppCompatActivity = context as AppCompatActivity
        // 创建File对象，用于存储拍照后的图片
        outputImage = File(activity.externalCacheDir, "output_image.jpg")
        if (outputImage.exists()) {
            outputImage.delete()
        }
        outputImage.createNewFile()
        imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Toast.makeText(context, "进入函数，执行fileProvider", Toast.LENGTH_SHORT).show()
            FileProvider.getUriForFile(context, "com.scut.fundialect.fileprovider", outputImage)



        } else {
            Toast.makeText(context, "进入函数，从file获取", Toast.LENGTH_SHORT).show()
            Uri.fromFile(outputImage)
        }
        // 启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        Toast.makeText(context, "进入函数，马上启动相机程序", Toast.LENGTH_SHORT).show()
        activity.startActivityForResult(intent, this.takePhoto)
    }


    public fun gitPicFromAlbum(context: Context) {

        // 打开文件选择器
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        // 指定只显示图片
        intent.type = "image/*"

        //把context强制转换成activity
        //然后执行调用此类的activity内的函数
        val mActivity :AppCompatActivity = context as AppCompatActivity
        mActivity.startActivityForResult(intent, this.fromAlbum)

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
                    openInputStream(imageUri))
                    bitmap = rotateIfRequired(bitmap)
                }
            }


        }

        return defaultUri()

    }

    private fun defaultUri() =
        Uri.parse("android.resource://" + "com.scut.fundialect" + "/" + R.raw.defaultpic)

    fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver) {
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
//}
///**
// * 使用Glide库加载网络图片
// * @author [FunnySaltyFish](https://funnysaltyfish.github.io)
// * @date 2021-07-14
// * @param context Context 合理的Context
// * @param url String 加载的图片URL
// * @param defaultImageId Int 默认加载的本地图片
// * @return MutableState<Bitmap?> 加载完成（失败为null）的Bitmap-State
// */
//fun loadImage(
//    context: Context,
//    url: String,
//    @SuppressLint("ResourceType") @DrawableRes defaultImageId: Int = R.raw.defaultpic
//): MutableState<Bitmap?> {
//    val TAG = "LoadImage"
//    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)
//
//    //为请求加上 Headers ，提高访问成功率
//    val glideUrl = GlideUrl(url,LazyHeaders.Builder().addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.67").build())
//
//    //先加载本地图片
//    Glide.with(context)
//        .asBitmap()
//        .load(defaultImageId)
//        .into(object : CustomTarget<Bitmap>() {
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                //自定义Target，在加载完成后将图片资源传递给bitmapState
//                bitmapState.value = resource
//            }
//
//            override fun onLoadCleared(placeholder: Drawable?) {}
//        })
//
//    //然后再加载网络图片
//    try {
//        Glide.with(context)
//            .asBitmap()
//            .load(glideUrl)
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    //自定义Target，在加载完成后将图片资源传递给bitmapState
//                    bitmapState.value = resource
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {}
//            })
//    } catch (glideException: GlideException) {
//        Log.d(TAG, "error: ${glideException.rootCauses}")
//    }
//
//    return bitmapState
//}
//
//@Composable
//fun LoadPicture(
//    url : String
//){
//
//    val imageState = loadImage(
//        context = LocalContext.current,
//        url = url,
//    )
//
//    Card(modifier = Modifier
//        .padding(4.dp)
//        .clickable { }) {
//        //如果图片加载成功
//        imageState.value?.let {
//            Image(
//                bitmap = it.asImageBitmap(),
//                contentDescription = "",
//                modifier = Modifier
//                    .padding(4.dp)
//                    .fillMaxWidth()
//            )
//        }
//    }
//}
//
////......
////
////LazyColumn {
////    val urls = arrayListOf<String>()
////    for (i in 500..550){urls.add("https://nyc3.digitaloceanspaces.com/food2fork/food2fork-static/featured_images/$i/featured_image.png")}
////    itemsIndexed(urls){ _ , url -> LoadPicture(url = url)}
////}
//
