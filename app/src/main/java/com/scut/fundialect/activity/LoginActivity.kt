package com.scut.fundialect.activity


import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.MyApplication
import com.scut.fundialect.R
import com.scut.fundialect.activity.culture.AlllTopicPage
import com.scut.fundialect.activity.culture.CulturePageMainAll
import com.scut.fundialect.activity.culture.TopicDetalPage
import com.scut.fundialect.activity.dubing.*
import com.scut.fundialect.activity.dubing.dubbingPageMainAll
import com.scut.fundialect.activity.learn.LearnCollectPageWithEvent
import com.scut.fundialect.activity.learn.LearnVideoPageWithEvent
import com.scut.fundialect.activity.login.AdWithEvent
import com.scut.fundialect.activity.myself.DraftVideoPlayerWithEvent
import com.scut.fundialect.activity.myself.MyselfPageWithEvent
import com.scut.fundialect.activity.myself.PicContainer
import com.scut.fundialect.activity.search.SearchOutcome
import com.scut.fundialect.activity.search.SearchPageWithEvent
import com.scut.fundialect.database.*
import com.scut.fundialect.database.helper.ModelVideoHelper
import com.scut.fundialect.database.helper.TopicHelper
import com.scut.fundialect.help.PicManager
import com.scut.fundialect.help.VideoHelper
import com.scut.fundialect.ui.theme.CustomBlue
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


//所有ACTIVITY都应该继承baseactivity，这样就可以执行一些全局操作。
@ExperimentalMaterialApi
class LoginActivity : BaseActivity() {
    val takePhoto = 1
    val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        VideoHelper.outputVideo = File(externalCacheDir, "output_image.jpg")
//        takePhotoBtn.setOnClickListener {
//            PicManager.gitPicFromAlbum(this)
//        }
        test()
        setContent {
            Navigation(this)
//            LogInPage(this,SampleData.conversationSample)
        }

   }
    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    @Composable
    private fun Navigation(context: Context){
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "DubbingPage") {
//            composable("AdPage") { AdPage() }
            composable("AdPage") { AdWithEvent(navController) }

            composable("loginPage") { LogInPageWithEvent(context,navController,SampleData.conversationSample) }
            composable("SearchPage/{location}"){
                    backStackEntry->
                backStackEntry.arguments?.getInt("searchStr")?.let {
                SearchPageWithEvent(context,navController,
                    it
                )
            } }
            composable("SearchOutcomePage/{searchStr}/{location}"){
                    backStackEntry->
                backStackEntry.arguments?.getInt("location")?.let {
                    SearchOutcome(navController, backStackEntry.arguments?.getString("searchStr"),
                        it
                    )
                }
            }

            composable("LearnVideoPage") { LearnVideoPageWithEvent(context,navController) }
            composable("LearnPage") { LearnCollectPageWithEvent(context,navController) }

            composable("CulturePage") { CulturePageMainAll(navController,context) }
            composable("TopicDetalPage/{topicId}") {
                    backStackEntry->
                backStackEntry.arguments?.getString("topicId")?.let {
                    TopicDetalPage(
                        navController,
                        it
                    )
                }
            }
            composable("allTopic") { AlllTopicPage(navController) }

            composable("DubbingPage") { dubbingPageMainAll(navController, context) }
            composable("OtherWorks") { OtherWorks(navController, context) }
            composable("HotSuggested") { HotSuggested(navController, context) }
            composable("InDubbingPage/{videoId}"){
                    backStackEntry->
                backStackEntry.arguments?.getInt("videoId")?.let {
                    InDubbingWithEvent(navController,
                        it
                    )
                }
            }
            composable("FinishDubbing/{videoId}"){
                    backStackEntry->
                backStackEntry.arguments?.getInt("videoId")?.let {
                    FinishDubbingWithEvent(navController,
                        it
                    )
                }
            }
            composable("ShareDubPage") { ShareDubPage(navController) }

            composable("MyselfPage") { MyselfPageWithEvent(navController) }
            composable("DraftVideoPlayer/{videoId}") {
                    backStackEntry->
                backStackEntry.arguments?.getString("videoId")?.let {
                    Toast.makeText(MyApplication.context,it.toString(),Toast.LENGTH_SHORT).show()
                    DraftVideoPlayerWithEvent(navController,
                        it
                    )
                }
            }
            composable("PicContainer/{imgId}") {
                    backStackEntry->
                backStackEntry.arguments?.getString("imgId")?.let {
                    Toast.makeText(MyApplication.context,it.toString(),Toast.LENGTH_SHORT).show()
                    PicContainer(it.toInt(),navController,

                    )
                }
            }




            /*...*/
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

        val dubedVideoDataBaseHelper =DubedVideoDataBaseHelper(this,"dubed.db",1)
        val dubedDB =dubedVideoDataBaseHelper.writableDatabase
        val topicDataBaseHelper = TopicDataBaseHelper(
            MyApplication.context,
            "topic.db",
            1
        )
        var topicDB: SQLiteDatabase = TopicHelper.topicDataBaseHelper.writableDatabase

        val learnVideoDataBaseHelper = LearnVideoDataBaseHelper(MyApplication.context,"Learn.db",1)
        val learnDB =learnVideoDataBaseHelper.writableDatabase
        //Toast.makeText(this, "LearnDB初始化成功", Toast.LENGTH_SHORT).show()

        val modelVideoDataBaseHelper =ModelVideoDataBaseHelper(this,"Model.db",1)
        val ModelDB =modelVideoDataBaseHelper.writableDatabase
        //

        val userInfoDataBaseHelper = UserInfoDataBaseHelper(this,"userinfo.db",1)
        val userinfoDB = userInfoDataBaseHelper.writableDatabase




    }


//    @Preview
//    @Composable
//    fun Preview(){
//        LogInPage(this, navController, SampleData.conversationSample)
//    }


    @Composable
    fun LogInPageWithEvent(
        context: Context,
        navController: NavHostController,
        messages: List<SampleData.Message>
    ){
        Image(
            painter = painterResource(id = R.drawable.loginpageback),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.height(60.dp)
            )
            Text(text = "登录",fontSize = 36.sp)
            var username by remember { mutableStateOf("") }
            var userpassword by remember { mutableStateOf("") }

            var buttonEnable by remember {
                mutableStateOf(false)
            }
            Spacer(
                modifier = Modifier.height(80.dp)
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
                modifier = Modifier
                    .width(120.dp)
                    .height(50.dp),
                onClick = {
                    gotoLearnActivity(context,navController)
                },
                enabled = buttonEnable,
                // Custom colors for different states
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = CustomBlue,
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
                fontSize = 20.sp,color = Color.White)
            }
            Row(
                modifier = Modifier.size(200.dp, 100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wx_friend),
                    modifier = Modifier.size(40.dp),
                    contentDescription = null
                )
                Image(
                    painter = painterResource(id = R.drawable.qq_friend),
                    modifier = Modifier.size(40.dp),
                    contentDescription = null
                )
                Image(
                    painter = painterResource(id = R.drawable.wb_news),
                    modifier = Modifier.size(40.dp),
                    contentDescription = null
                )
            }
        }
        Box(contentAlignment = Alignment.BottomCenter,modifier = Modifier.fillMaxSize()) {
            Text(text = "注册|帮助")
        }
    }


    private fun gotoLearnActivity(context: Context, navController: NavController) {
        navController.navigate("LearnPage")
    }

    private fun checkPassWord(username:String, userPassWord:String):Boolean {

        if(username != "" && userPassWord != ""
//          &&  Public.isComplite()
        ){
            //设置按钮为高亮
            return true
        }
        return true
    }


}
