package com.scut.fundialect.activity.culture


import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.R
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.database.helper.TopicHelper
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.ui.theme.CustomOrange
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
                        .height(100.dp),
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
            .verticalScroll(rememberScrollState())
    ) {
        /**
         * 上面的选择列表
         *
         * */
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
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
        ClickHorBoxWithEvent()

    }
}
@Composable
@Preview
fun ClickHorBoxWithEvent(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val info =  TopicHelper.getCollectedTopic()
        info.forEachIndexed {index, it->
            ClickHorBox(onClick = { TODO()},index.toString(),it.videoName)
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

}
@Composable
@Preview
fun pre(){
    Box(modifier = Modifier.size(400.dp,40.dp)) {
        ClickHorBox(onClick = {},"1","列宁的家乡是不是都喜欢喝？")

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
            .height(40.dp),
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