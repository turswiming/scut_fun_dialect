package com.scut.fundialect.activity


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scut.fundialect.R
import com.scut.fundialect.database.*
import com.scut.fundialect.help.PicManager
import com.scut.fundialect.help.VideoHelper
import com.scut.fundialect.ui.theme.ComposeTutorialTheme
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File




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
            PicManager.gitPicFromAlbum(this)
        }
        test()
   }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
        val uri = PicManager.getPicUri(requestCode, resultCode, data,contentResolver)
        imageView.setImageURI(uri)
    }
    fun test(){
        val cityDataBaseHelper = CityDataBaseHelper(this,"city.db",1)
        val cityDB = cityDataBaseHelper.writableDatabase
        //Toast.makeText(this, "cityDB初始化成功", Toast.LENGTH_SHORT).show()

        val dubedVideoDataBaseHelper =DubedVideoDataBaseHelper(this,"dubed.db",1)
        val dubedDB =dubedVideoDataBaseHelper.writableDatabase
        //Toast.makeText(this, "dubedDB初始化成功", Toast.LENGTH_SHORT).show()

        val learnVideoDataBaseHelper = LearnVideoDataBaseHelper(this,"Learn.db",1)
        val learnDB =dubedVideoDataBaseHelper.writableDatabase
        //Toast.makeText(this, "LearnDB初始化成功", Toast.LENGTH_SHORT).show()

        val modelVideoDataBaseHelper =ModelVideoDataBaseHelper(this,"Model.db",1)
        val ModelDB =dubedVideoDataBaseHelper.writableDatabase
        //Toast.makeText(this, "ModelDB初始化成功", Toast.LENGTH_SHORT).show()

        val userInfoDataBaseHelper = UserInfoDataBaseHelper(this,"userinfo.db",1)
        val userinfoDB = userInfoDataBaseHelper.writableDatabase




    }
}
data class Message(val author:String,val body:String)
@Composable
fun MessageCard(msg:Message) {
    Row(
        modifier = Modifier.padding(all = 8.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape).border(1.5.dp,MaterialTheme.colors.secondary, CircleShape)
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Column() {
            Text(text = msg.author)
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            Text(text = msg.body)
        }
    }


}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(
        msg = Message("name","this is an info")
    )
}
