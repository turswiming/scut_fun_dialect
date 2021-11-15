package com.scut.fundialect.activity


import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scut.fundialect.R
import com.scut.fundialect.activity.culture.CultureActivity
import com.scut.fundialect.activity.learn.LearnActivity
import com.scut.fundialect.database.*
import com.scut.fundialect.help.PicManager
import com.scut.fundialect.help.VideoHelper
import com.scut.fundialect.ui.theme.ComposeTutorialTheme
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


//所有ACTIVITY都应该继承baseactivity，这样就可以执行一些全局操作。
class LoginActivity : BaseActivity() {
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
        setContent {
            loginPage(this,SampleData.conversationSample)
        }

   }



    //这个函数会在下层Activity返回的时候执行。
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
        val uri = PicManager.getPicUri(requestCode, resultCode, data,contentResolver)
        imageView.setImageURI(uri)
    }


    private fun test(){
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
        //

        val userInfoDataBaseHelper = UserInfoDataBaseHelper(this,"userinfo.db",1)
        val userinfoDB = userInfoDataBaseHelper.writableDatabase

//        setContent {
//            PreviewConversation()
//        }



    }


    @Preview
    @Composable
    fun preview(){
        loginPage(this,SampleData.conversationSample)
    }
    @Composable
    fun loginPage(context:Context,messages:List<SampleData.Message>){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.height(40.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(3.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            var username by remember { mutableStateOf("") }
            var userpassword by remember { mutableStateOf("") }

            var buttonEnable by remember {
                mutableStateOf(false)
            }
            Spacer(
                modifier = Modifier.height(40.dp)
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("用户名/邮箱") }
            )
            OutlinedTextField(
                value = userpassword,
                onValueChange = { userpassword = it },
                label = { Text("密码") }
            )
            Spacer(
                modifier = Modifier.height(40.dp)
            )
            buttonEnable = checkPassWord(username,userpassword)
            Button(
                modifier = Modifier.width(120.dp).height(50.dp),
                onClick = {
                    gotoLearnActiviy(context)
                },
                enabled = buttonEnable,
                // Custom colors for different states
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    disabledBackgroundColor = MaterialTheme.colors.onBackground
                        .copy(alpha = 0.2f)
                        .compositeOver(MaterialTheme.colors.background)
                    // Also contentColor and disabledContentColor
                ),
                // Custom elevation for different states
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    disabledElevation = 2.dp,
                    // Also pressedElevation
                )
            ) {
                Text(text = "登录",
                fontSize = 20.sp)
            }
        }
    }


    private fun gotoLearnActiviy(context: Context) {
        val intent = Intent(context, LearnActivity::class.java)
        startActivity(intent)
    }

    private fun checkPassWord(username:String,userpassword:String):Boolean {
        if(username != "" && userpassword != ""){
            //设置按钮为高亮
            return true
        }
        return false
    }
    /**
     * 下面的是学习用范例，别删，但是也没啥用
     * **/
    fun gotoActivity():Unit{

    }
    @Composable
    fun MessageCard(msg: SampleData.Message) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
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
            Column(
                modifier = Modifier.clickable {
                    isExpanded = !isExpanded
                    gotoActivity()
                }

            ) {
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
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
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


    @Composable
    fun PreviewMessageCard() {
        MessageCard(
            msg = SampleData.Message("name", "this is an info")
        )
    }

    @Composable
    fun Conversation(messages: List<SampleData.Message>) {
        LazyColumn {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }

    //@Preview
    @Composable
    fun PreviewConversation() {
        ComposeTutorialTheme {
            Conversation(SampleData.conversationSample)
        }
    }


}
