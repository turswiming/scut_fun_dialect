package com.scut.fundialect.activity


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

        setContent {
            PreviewConversation()
        }



    }
}
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
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colors.error, CircleShape)
        )
        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor: Color by animateColorAsState(
            if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(text = msg.author)
            Spacer(
                modifier = Modifier.height(4.dp)
            )
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }

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

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        Conversation(SampleData.conversationSample)
    }
}

