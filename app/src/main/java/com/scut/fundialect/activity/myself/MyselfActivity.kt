package com.scut.fundialect.activity.myself

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.activity.dubing.MyPictureShower
import com.scut.fundialect.database.helper.ModelVideoHelper
import com.scut.fundialect.database.helper.ModelVideoHelper.getCollectedModelVideo
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.help.PicManager.getBitmapFromUri
import com.scut.fundialect.ui.theme.ComposeTutorialTheme
import com.scut.fundialect.ui.theme.CustomOrange
import com.scut.fundialect.ui.theme.Transparent
import kotlinx.coroutines.launch

class MyselfActivity : BaseComposeActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    MyselfPage()
                }
            }
        }
    }

}

@ExperimentalPagerApi
@Composable
fun MyselfPageWithEvent(navController: NavHostController) {
    val userInfo =  UserHelpr.UserInfo(UserHelpr.userNow)
    Scaffold(
        bottomBar = {
            /**
             * *
             * * 下面的bar*
             * *
             * */
            MyButtonAppBar(
                colorMode = ColorMode.light,
                gotoAnotherActivity = {
                    gotoAnotherActivity(navController,it)}
                ,
                onStateChange = {

                },
                initPageIndex = 3
            )
                    },
        topBar = {
        /**
         *
         * 上面的bar。包括頭像，定位，設置等
         *
         * */
        MyselfTopBar(
            onSettingIconClick={},
            onImageClick={},
            userInfo.userPicFile,
            "广州市",
            userInfo.userNickName
        )
    }
    ) {
        MyselfMainPage(navController,
            involvedTopic ={},
            myStep = {},
            getVideoData = {
                getCollectedModelVideo()
            }
        )
    }

}
@ExperimentalPagerApi
@Composable
@Preview
fun preview(){
//    MyselfMainPage(
//        involvedTopic ={},
//        myStep = {},
//        getVideoData = {getCollectedModelVideo()}
//    )
}
@ExperimentalPagerApi
@Composable
@Preview
fun preview2(){
//    MyselfTopBar(onSettingIconClick={},onImageClick={},toUriStr(com.scut.fundialect.R.raw.defaultpic),"广州市","樱桃小丸子的丸~")

}
fun toUriStr(image:Int):String{
    return "android.resource://com.scut.fundialect/$image"
    //default "${toUriStr(com.scut.fundialect.R.raw.defaultpic)}"

}
@ExperimentalPagerApi
@Composable
fun MyselfMainPage(
    navController: NavHostController,
    involvedTopic: () -> Unit,
    myStep: () -> Unit,
    getVideoData:()-> List<ModelVideoHelper.ModelVideoInfo>,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
    ) {
        /**
         *
         * 上面的方框，包括打卡地點，粉絲，關注，詞庫等
         *
         * */
        MyBox()
        Spacer(modifier = Modifier.height(15.dp))

        /**
         *
         * 中間的兩個方框，參與話題和我的足跡
         *
         * */
        Row(
            Modifier
//                .height(60.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyButton2(onclick = { involvedTopic() }, text1 = "参与话题",)
            MyButton2(onclick = { myStep() }, text1 = "我的足迹",)

        }
        Spacer(modifier = Modifier.height(15.dp))

        val Strs = listOf("收藏","作品","草稿")
        var state by remember {
            mutableStateOf(0)
        }
        val pagerState = rememberPagerState(
            //总页数
            pageCount = 3,
            //预加载的个数
            initialOffscreenLimit = 1,
            //是否无限循环
            infiniteLoop = true,
            //初始页面
            initialPage = 0
        )
        val shareScope = rememberCoroutineScope()
        TabRow(
            selectedTabIndex = state,
            Modifier
                .width(200.dp)
                .background(Color.Transparent),
            contentColor = CustomOrange,
            backgroundColor = Color.Transparent
        ) {
            Strs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title, fontSize = 14.sp,color = Color.Black) },
                    modifier = Modifier
                        .background(Color.Transparent),
                    selected = state == index,
                    onClick = {
                        state = index
                        shareScope.launch { // 创建一个新协程
                            pagerState.animateScrollToPage(index)

                        }
//                        LaunchedEffect(pagerState.currentPage) {

                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(540.dp),
        ) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                var videos = getVideoData()
//                Toast.makeText(context,"${videos.size}",Toast.LENGTH_SHORT).show()
                videos.forEach {
                    MyPictureShower(it,navHostController = navController,
                        clickable = {
                            if(page==0){
                                TODO()
                            }
                            if(page==1){
                                TODO()
                            }
                            if(page==2){
                                Toast.makeText(context,"准备导航",Toast.LENGTH_SHORT).show()
                                navController.navigate("DraftVideoPlayer/${it.videoId}")
                            }
                        }
                    )

                }

            }
            //自动滚动
//            LaunchedEffect(pagerState.currentPage) {
//                if (pagerState.pageCount > 0) {
//                    delay(2000)
//                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                }
//            }

//            Surface(
//                shape = RoundedCornerShape(15.dp),
//                modifier = Modifier.fillMaxSize()
//            ) {
//
//            }
        }
    }
}

@Composable
private fun MyBox() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        /**
         *
         * 上面綠色的背景圖
         *
         * */
        Image(
            painter = painterResource(id = R.drawable.c),
            contentDescription = "綠色的背景圖",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                /**
                 * 綠色背景上面的文字，你已打卡等
                 * */
                Column() {
                    Text(text = "你已打卡",color = Color.White)
                    Text(text = "3个城市的方言",color = Color.White)
                    Image(
                        painter = painterResource(id = R.drawable.myself3),
                        contentDescription = null,
                        Modifier
                            .clickable { TODO() }
                            .size(45.dp, 30.dp),
                        alignment = Alignment.BottomStart
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.myselfchinamap),
                    contentDescription = null,
                    Modifier
                        .clickable { TODO() }
                        .size(120.dp)

                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(15.dp),
                color = Color.White,
                elevation = 10.dp

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    /**
                     * 粉絲，關注和詞庫這三個按鈕
                     * */
                    MyButton(onclick = {}, text1 = "粉丝", text2 = "123")
                    MyButton(onclick = {}, text1 = "关注", text2 = "13")
                    MyButton(onclick = {}, text1 = "词库", text2 = "100")

                }
            }
        }
    }
}

@Composable
private fun MyButton(onclick:()->Unit,text1:String,text2:String) {
    Surface(
        modifier = Modifier
            .width(80.dp)
            .height(50.dp)
            .clickable {
                onclick()
            },
        shape = RoundedCornerShape(15.dp),
        color = Color.White,
        elevation = 10.dp

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = text1)
            Text(text = text2)
        }

    }
}
@Composable
private fun MyButton2(onclick:()->Unit,text1:String) {
    Surface(
        modifier = Modifier
            .width(150.dp)
            .height(50.dp)
            .clickable {
                onclick()
            },
        shape = RoundedCornerShape(15.dp),
        color = Color.White,
        elevation = 2.dp

    ) {
        Image(
            painter = painterResource(id = R.drawable.myselficon2),
            contentDescription = null,
            contentScale= ContentScale.Crop,
            alignment = Alignment.TopCenter,
        modifier =  Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text1,color = Color.White)
            Image(
                painter = painterResource(id = R.drawable.myselficon),
                contentDescription = null,
                modifier = Modifier.size(40.dp,25.dp)
            )
        }

    }
}

@Composable
fun MyselfTopBar(
    onSettingIconClick: () -> Unit,
    onImageClick: () -> Unit,
    imageUri:String,
    Location:String,
    name:String,
) {
    /**
     *
     * 上面的bar。包括頭像，定位，設置等
     *
     * */
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        backgroundColor = Transparent,
        elevation = 0.dp,
        content = {
            Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.Bottom){
                    /**
                     *
                     * 頭像
                     *
                     * */
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(0.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(imageUri),
                            contentDescription ="頭像",
                            contentScale = ContentScale.Crop
                        )

                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column() {
                        /**
                         *
                         * 城市
                         *
                         * */
                        Row() {
                            Text(text = Location)
                            /**
                             *
                             * 城市右邊的定位圖標
                             *
                             * */
                            Image(
                                painter = painterResource(id = R.drawable.myself1),
                                contentDescription =null ,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        /**
                         *
                         * 名字
                         *
                         * */
                        Text(text = name)
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.myself2),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }
        }

    )
}
