package com.scut.fundialect.activity.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.SearchTopAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.activity.culture.CultureShowBox
import com.scut.fundialect.activity.culture.toUriStr
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.database.helper.LearnVideoHelper.search
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.help.toDateStr
import com.scut.fundialect.ui.theme.FontGray

@Composable
fun SearchOutcome(
    navController: NavHostController,
    searchStrIncome:String?,
    location:Int
){
//    Toast.makeText(context,"载入compose",Toast.LENGTH_SHORT).show()
    Image(
        painter = painterResource(id = R.drawable.serachoutcomepage),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter
    )
    var searchStrIncome1=""
    if (searchStrIncome!=null){
        searchStrIncome1 = searchStrIncome
    }
    var searchStr by remember{ mutableStateOf(searchStrIncome1)}
    Scaffold(
        backgroundColor = Color.Transparent,
        topBar={
            SearchTopAppBar(searchStrIncome1,
                onReturn = {
                    navController.popBackStack()
                           },
                onSearch = {
                    searchStr = it
                }
            ,onValueChange = {searchStr =it })
        },
        bottomBar={
            MyButtonAppBar(
                colorMode = ColorMode.light,
                gotoAnotherActivity = {
                    gotoAnotherActivity(navController,it)
                },
                onStateChange = {},
                initPageIndex = location
            )
        }

    ){
        val results = search(searchStr)
        LazyColumn(content = {
            items(results.size) {index->
                if (location==0||location==2){
                    SearchVideoBoxWithEvent(results[index])

                }
                if (location==1){
                    SearchVideoBox2WithEvent(results[index])
                }
            }
        })
    }
}

@Composable
fun SearchVideoBoxWithEvent(videoInfo: LearnVideoHelper.VideoInfo){
    val userinfo =  UserHelpr.UserInfo(videoInfo.videoUploaderId)
    Box(Modifier.width(210.dp))
    SearchVideoBox(
        videoInfo.videoPicUri,
        videoInfo.videoName,
        userinfo.userPicFile,
        userinfo.userNickName,
        videoInfo.videoUpdateTime.toLong().toDateStr(),
    )
}
@Composable
fun SearchVideoBox2WithEvent(videoInfo: LearnVideoHelper.VideoInfo){
    val userinfo =  UserHelpr.UserInfo(videoInfo.videoUploaderId)
    Box(Modifier.width(210.dp))
    CultureShowBox(
        clickable = {
        },
        videoInfo.videoName,
        videoInfo.videoIntroduce,
        videoInfo.videoPicUri,
        videoInfo.videoPicUri,
        userinfo.userNickName,
        videoInfo.videoUploaderId.toLong().toDateStr(),
        videoInfo.videoLike,
    )}
@Composable
@Preview
fun SearchVideoBoxPreview(){
    Box(Modifier.width(200.dp))
    SearchVideoBox(
        "android.resource://com.scut.fundialect/${R.raw.defaultpic}",
        "論持久戰",
        "android.resource://com.scut.fundialect/${R.raw.defaultpic}",
        "李德同志",
        "11-21 18:26",
    )
}
@Composable
fun SearchVideoBox(imgUri:String,videoName:String,uploaderImgUri:String,uploaderName:String,time:String){
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp).padding(10.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
            ){
                Image(painter = rememberImagePainter(imgUri),
                    contentDescription =null,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight()
            ) {
                Text(text = videoName,fontSize = 20.sp)
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = rememberImagePainter(uploaderImgUri), contentDescription =null,modifier = Modifier
                        .size(20.dp)
                    )
                    Text(text = uploaderName,color = FontGray)

                }

            }
            Text(text = time,color = FontGray)

        }
    }

}