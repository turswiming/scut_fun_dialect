package com.scut.fundialect.activity.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.scut.fundialect.R
import com.scut.fundialect.activity.compose.SearchTopAppBar
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.help.toDateStr
import com.scut.fundialect.ui.theme.FontGray

@Composable
fun SearchOutcome(navController: NavHostController,searchStrIncome:String?){
    var searchStr by remember{ mutableStateOf(searchStrIncome)}
    Scaffold(
        topBar={
            SearchTopAppBar(
                onReturn = {
                    navController.popBackStack()
                           },
                onSearch = {
                    searchStr = it
                }
            )
        },
        bottomBar={

        }

    ){

    }
}

@Composable
fun SearchVideoBoxWithEvent(videoInfo: LearnVideoHelper.VideoInfo){
    val userinfo =  UserHelpr.UserInfo(videoInfo.videoUploaderId)
    Box(Modifier.width(200.dp))
    SearchVideoBox(
        videoInfo.videoPicUri,
        videoInfo.videoName,
        userinfo.userPicFile,
        userinfo.userNickName,
        videoInfo.videoUpdateTime.toLong().toDateStr(),
    )
}
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
            .height(100.dp)
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
                modifier = Modifier.width(160.dp).fillMaxHeight()
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