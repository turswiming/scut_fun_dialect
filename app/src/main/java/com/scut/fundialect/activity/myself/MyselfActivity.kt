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
             * * ?????????bar*
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
         * ?????????bar????????????????????????????????????
         *
         * */
        MyselfTopBar(
            onSettingIconClick={},
            onImageClick={},
            userInfo.userPicFile,
            "?????????",
            userInfo.userNickName
        )
    }
    ) {
        MyselfMainPage(navController,
            involvedTopic ={},
            myStep = {}
        ) {
            getCollectedModelVideo()
        }
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
//    MyselfTopBar(onSettingIconClick={},onImageClick={},toUriStr(com.scut.fundialect.R.raw.defaultpic),"?????????","?????????????????????~")

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
    getVideoData: () -> List<ModelVideoHelper.ModelVideoCathe>,
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
    ) {
        /**
         *
         * ??????????????????????????????????????????????????????????????????
         *
         * */
        MyBox(navController)
        Spacer(modifier = Modifier.height(15.dp))

        /**
         *
         * ???????????????????????????????????????????????????
         *
         * */
        Row(
            Modifier
//                .height(60.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyButton2(onclick = { involvedTopic() }, text1 = "????????????",)
            MyButton2(onclick = { myStep() }, text1 = "????????????",)

        }
        Spacer(modifier = Modifier.height(15.dp))

        val Strs = listOf("??????","??????","??????")
        var state by remember {
            mutableStateOf(0)
        }
        val pagerState = rememberPagerState(
            //?????????
            pageCount = 3,
            //??????????????????
            initialOffscreenLimit = 1,
            //??????????????????
            infiniteLoop = true,
            //????????????
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
                        shareScope.launch { // ?????????????????????
                            pagerState.animateScrollToPage(index)

                        }
//                        LaunchedEffect(pagerState.currentPage) {

                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))

        val videos = getVideoData()
//        Toast.makeText(context,videos[0].videoId.toString(),Toast.LENGTH_SHORT).show()
//        Toast.makeText(context,videos[1].videoId.toString(),Toast.LENGTH_SHORT).show()
//
//        Toast.makeText(context,videos[2].videoId.toString(),Toast.LENGTH_SHORT).show()

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
//                Toast.makeText(context,"${videos.size}",Toast.LENGTH_SHORT).show()
                MyPictureShower(videos[0],navHostController = navController,
                    clickable = {
                        if(page==0){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[0].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[0].videoId.toString()}")                        }
                        if(page==1){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[0].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[0].videoId.toString()}")                        }
                        if(page==2){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[0].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[0].videoId.toString()}")
                        }
                    }
                )
                MyPictureShower(videos[1],navHostController = navController,
                    clickable = {
                        if(page==0){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[1].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[1].videoId.toString()}")                        }
                        if(page==1){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[1].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[1].videoId.toString()}")                        }
                        if(page==2){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[1].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[1].videoId.toString()}")
                        }
                    }
                )
                MyPictureShower(videos[2],navHostController = navController,
                    clickable = {
                        if(page==0){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[2].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[2].videoId.toString()}")
                        }
                        if(page==1){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[2].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[2].videoId.toString()}")
                        }
                        if(page==2){
                            Toast.makeText(context,"DraftVideoPlayer/${videos[2].videoId}",Toast.LENGTH_SHORT).show()
                            navController.navigate("DraftVideoPlayer/${videos[2].videoId.toString()}")
                        }
                    }
                )

            }
            //????????????
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
private fun MyBox(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        shape = RoundedCornerShape(15.dp),
    ) {
        /**
         *
         * ????????????????????????
         *
         * */
        Image(
            painter = painterResource(id = R.drawable.c),
            contentDescription = "??????????????????",
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
                 * ?????????????????????????????????????????????
                 * */
                Column() {
                    Text(text = "????????????",color = Color.White)
                    Text(text = "3??????????????????",color = Color.White)
                    Image(
                        painter = painterResource(id = R.drawable.myself3),
                        contentDescription = null,
                        Modifier
                            .clickable {
                                navController.navigate("PicContainer/${R.drawable.mymap}")
                            }
                            .size(45.dp, 30.dp),
                        alignment = Alignment.BottomStart
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.myselfchinamap),
                    contentDescription = null,
                    Modifier
                        .clickable {
                            navController.navigate("PicContainer/${R.drawable.mymap}")
                        }
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
                     * ???????????????????????????????????????
                     * */
                    MyButton(onclick = {
                        navController.navigate("PicContainer/${R.drawable.myfans}")
                    }, text1 = "??????", text2 = "123")
                    MyButton(onclick = {
                        navController.navigate("PicContainer/${R.drawable.mysight}")
                    }, text1 = "??????", text2 = "13")
                    MyButton(onclick = {
                        navController.navigate("LearnPage")
                    }, text1 = "??????", text2 = "100")

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
     * ?????????bar????????????????????????????????????
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
                     * ??????
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
                            contentDescription ="??????",
                            contentScale = ContentScale.Crop
                        )

                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column() {
                        /**
                         *
                         * ??????
                         *
                         * */
                        Row() {
                            Text(text = Location)
                            /**
                             *
                             * ???????????????????????????
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
                         * ??????
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
