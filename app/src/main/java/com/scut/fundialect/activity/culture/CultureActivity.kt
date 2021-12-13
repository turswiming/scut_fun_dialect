package com.scut.fundialect.activity.culture


import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.R
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.database.helper.TopicHelper
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.help.toDateStr
import com.scut.fundialect.ui.theme.CustomOrange
import com.scut.fundialect.ui.theme.FontGray
import com.scut.fundialect.ui.theme.Transparent

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun CulturePageMainAll(
    navController: NavHostController,
    context: Context
) {
    CulturePageWithEvent(
        navController,
        context,
        CulturePageContent = {
            DubMainPage(navController,onCheckMore = { TODO()})
        },
        barContent = {
            /**
             * 左上角的俩大字
             *
             * */
            Text(
                text = "文化",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp)
            )
            /**
             *
             * 搜索按钮
             *
             *
             * */
            Image(
                painter = painterResource(id = R.drawable.searchwhite),
                contentDescription = "search icon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        navController.navigate("SearchPage/${2}")

                    }

            )
        }
    )
}
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun CulturePageWithEvent(
    navController: NavHostController,
    context: Context,
    CulturePageContent:@Composable () -> Unit,
    barContent:@Composable () -> Unit

) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.culturebackground),
            contentDescription = null
        )
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                /**
                 *
                 * 这里面是一整个上面的bar，包括搜索按钮和选择城市的按钮
                 *
                 * */
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    backgroundColor = Transparent,
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = Transparent),

                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            barContent()

                        }
                    }
                }
            },
            bottomBar ={
                MyButtonAppBar(
                    colorMode = ColorMode.light,
                    gotoAnotherActivity = {gotoAnotherActivity(navController,it)},
                    onStateChange = {

                    },
                    initPageIndex = 1
                )
            }

        ) {
            CulturePageContent()
        }
    }

}


@ExperimentalPagerApi
@Composable
fun DubMainPage(navController:NavHostController,
                onCheckMore:()->Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()).padding(15.dp)
    ) {
        /**
         * 上面的选择列表
         *
         * */
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),horizontalArrangement = Arrangement.SpaceBetween,verticalAlignment = Alignment.CenterVertically
        ) {
            var state by remember {
                mutableStateOf(0)
            }
            TabRow(
                selectedTabIndex = state,
                Modifier
                    .width(200.dp)
                    .background(Color.Transparent),
                contentColor = Color.White,
                backgroundColor = Color.Transparent
            ) {
                val strs = listOf("热播榜","最新榜")
                strs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title, fontSize = 14.sp,color = Color.Black) },
                        modifier = Modifier
                            .background(Color.Transparent),
                        selected = state == index,
                        onClick = {
                            state = index

                        }
                    )
                }
            }
            Row(Modifier.clickable {
                onCheckMore()
            }
            ) {
                Text(text = "查看更多")
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    Modifier
                        .rotate(180F)
                        .size(20.dp),
                )
            }
        }
        /**
         * 三个Bar
         *
         * */
        ClickHorBoxWithEvent(onclick = {
            navController.navigate("TopicDetalPage/${it}")
        })
        Spacer(modifier = Modifier.size(20.dp))

        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.clickable {
                navController.navigate("HotSuggested")
            }
            ) {
                Text(text = "广州热搜",fontSize = 18.sp)
                Image(
                    painter = painterResource(id = R.drawable.myself1),
                    contentDescription = null,
                    Modifier
                        .rotate(180F)
                        .size(20.dp),
                )
            }
            Row(Modifier.clickable {
                navController.navigate("HotSuggested")
            }
            ) {
                Text(text = "查看更多")
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    Modifier
                        .rotate(180F)
                        .size(20.dp),
                )
            }

        }
        Spacer(modifier = Modifier.size(10.dp))

        Row(
            Modifier
//                .height(60.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val info =  TopicHelper.getCollectedTopic()
            MyButton2(
                onclick = {
                navController.navigate("TopicDetalPage/${info[0].videoId}")
            },
                text1 = info[0].videoName,
            )
            MyButton2(
                onclick = {
                navController.navigate("TopicDetalPage/${info[1].videoId}")
                          },
                text1 = info[0].videoName,
            )

        }
        Spacer(modifier = Modifier.size(20.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
            Row(
                Modifier.clickable {
                // TODO: 2021/12/13

//                navController.navigate("HotSuggested")
            }
            ) {
                Text(text = "热门话题",fontSize = 18.sp)
                Image(
                    painter = painterResource(id = R.drawable.myself1),
                    contentDescription = null,
                    Modifier
                        .rotate(180F)
                        .size(20.dp),
                )
            }
            Row(Modifier.clickable {
                // TODO: 2021/12/13
//                navController.navigate("HotSuggested")
            }
            ) {
                Text(text = "查看更多")
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    Modifier
                        .rotate(180F)
                        .size(20.dp),
                )
            }

        }
        Spacer(modifier = Modifier.size(10.dp))

        val info =  TopicHelper.getCollectedTopic()
        info.forEach {
            CultureShowBoxWithEvent(it,clickable = {
                navController.navigate("TopicDetalPage/${it.videoId}")
            })
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}


@Composable

fun CultureShowBoxWithEvent(it:TopicHelper.TopicCathe,clickable:()->Unit){
    val videoUploarder = UserHelpr.UserInfo(it.videoUploaderId)
    CultureShowBox(
        clickable = {
            clickable()
        },
        it.videoName,
        it.videoIntroduce,
        it.videoPicUri,
        toUriStr(R.raw.defaultuserpic),
        videoUploarder.userNickName,
        it.videoUploaderId.toLong().toDateStr(),
        it.videoLike,
    )
}
@Composable
fun CultureShowBox(
    clickable:()->Unit,
name:String,
    introduce:String,
    imguri:String,
    userImgUri:String,
    username:String,
    time:String,
    likedNum:Int
){
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp).clickable {
                clickable()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text =name,
                Modifier
                    .padding(20.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxWidth()
                    .height(30.dp),textAlign = TextAlign.Left,
                fontSize = 20.sp,
            )
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            ) {
                Image(
                    painter = rememberImagePainter(imguri),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                )
                Box(modifier = Modifier
                    .fillMaxSize(),
//                    .background(color = Color.Green),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = introduce, color = Color.White, fontSize = 18.sp)

                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(                                                verticalAlignment = Alignment.CenterVertically

                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            ) {
                                Image(painter = rememberImagePainter(userImgUri), contentDescription = null)
                            }
                            Spacer(modifier = Modifier.size(10.dp))

                            Text(text = username,color = Color.White,)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            horizontalArrangement = Arrangement.End,
                        ) {
                            Text(text = time,color = Color.White,)
                            Spacer(modifier = Modifier.size(10.dp))
                            Image(painter = painterResource(id = R.drawable.topic2), contentDescription = null,modifier = Modifier.size(15.dp))
                            Spacer(modifier = Modifier.size(10.dp))

                            Text(text = likedNum.toString(),color = Color.White,)
                        }
                    }

                }
            }
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
fun ClickHorBoxWithEvent(onclick:(Int)->Unit){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val info =  TopicHelper.getCollectedTopic()
        info.forEachIndexed {index, it->
            ClickHorBox(onClick = { onclick(it.videoId)},(index+1).toString(),it.videoName)
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}


@Composable
fun ClickHorBox(
    onClick:()->Unit,num:String,text:String
){
    Button(colors = buttonColors(
        backgroundColor= Color.White,),
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.size(0.dp))
                Text(text = num, color = CustomOrange)
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = text, color = Color.Black)
            }
            Box(contentAlignment = Alignment.CenterEnd, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.dub1),
                    contentDescription =null,
                    Modifier.size(30.dp)
                )
            }
        }

    }
}

fun toUriStr(image:Int):String{
    return "android.resource://com.scut.fundialect/$image"
    //default "${toUriStr(com.scut.fundialect.R.raw.defaultpic)}"

}