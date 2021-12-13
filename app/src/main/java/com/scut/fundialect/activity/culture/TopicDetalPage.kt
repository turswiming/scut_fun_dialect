package com.scut.fundialect.activity.culture

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.dubing.DubingPageWithEvent
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.database.helper.TopicHelper
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.FontGray


@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun TopicDetalPage(navController: NavHostController,topicId:Int){
    Toast.makeText(context,"开始载入compose",Toast.LENGTH_SHORT).show()
    val topicInfo = TopicHelper.TopicInfo(topicId)
    Toast.makeText(context,"开始载入用户数据",Toast.LENGTH_SHORT).show()
    val uploader = UserHelpr.UserInfo(topicInfo.videoUploaderId)
    DubingPageWithEvent(
        navController = navController,
        context = context,
        barContent ={
            /**
             * 左上角的俩大字
             *
             * */
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "search icon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        navController.popBackStack()
                    }

            )
            Text(text = "回答",color = Color.White,fontSize = 20.sp)
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
                        navController.navigate("SearchPage/${1}")

                    }

            )
        },
        dubPageContent={
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                TopBox(topicInfo.videoName)
                Text(
                    text = TopicHelper.getCommitNumber(topicId).toString() +"条评论",
                    color = FontGray
                )
                MiddleBox(
                    uploader.userNickName,
                    topicInfo.videoIntroduce,
                    topicInfo.videoPicUri
                )
                androidx.compose.material.Surface(
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    val commentInfos =

                    LazyColumn(

                    ){
                        val videoCommit  = TopicHelper.getVideoComment(topicId)

                        items(videoCommit.size){index->
                            /**
                             *
                             * 这里是 每一个 评论。
                             *
                             * */
                            /**
                             *
                             * 这里是 每一个 评论。
                             *
                             * */
                            Comment(it = videoCommit[index])
                        }
                    }
                }
            }


        }
    )
}
/**
 *
 * 这里是每一个评论。
 *
 * */
@Composable
fun Comment(it: TopicHelper.CommentInfo) {
    var isLike by remember {
        mutableStateOf(it.isLiked )
    }
    var numberLiked by remember {
        mutableStateOf(it.numberLiked )
    }
    val commenterInfo = UserHelpr.UserInfo(it.commenterId)

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, 5.dp),
    ) {
        /**这个surfface里面是用户的头像*/

        Surface(
            shape = CircleShape,
            /**这个是描边*/
            border = BorderStroke(1.dp, Color.Black)

        ){
            Image(
                painter = rememberImagePainter(commenterInfo.userPicFile),
                contentDescription = "头像",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)

            )
        }

        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(200.dp)
                .height(80.dp)) {
            Text(
                text = commenterInfo.userNickName,
                fontSize = 14.sp,)

            Text(
                text = it.comment,
                fontSize = 12.sp,)

        }
        Column(
            modifier = Modifier
                .width(40.dp)
                .clickable {
                    //处理compose内ui层逻辑
                    isLike = !isLike
                    if (isLike) {
                        numberLiked += 1
                    } else {
                        numberLiked -= 1
                    }
                    LearnVideoHelper.switchCommentLike(it.CommitId)
                },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = switch(
                        R.drawable.good_red,
                        R.drawable.good_gray,
                        isLike
                    )
                ),
                contentDescription = "点赞"
            )
            Text(
                text = numberLiked.toString(),
                fontSize=12.sp,
            )
        }

    }

}


@Composable
fun TopBox(text:String){
    Surface(
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp, 0.dp, 0.dp, 0.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize() ,
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.xiongmao),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp),
                contentScale = ContentScale.Crop,
                alignment = Alignment.CenterEnd)
        }
    }
}
@Composable
fun MiddleBox(name:String,introduce:String,imgUri:String){
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
            ) {
                androidx.compose.material.Surface(
                    shape = CircleShape,
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(imgUri),
                        contentDescription =null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = name,fontSize = 18.sp)
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = Color.Gray)
            )
            Text(text = introduce, modifier = Modifier.fillMaxWidth(),maxLines = 4)
        }
        Box(modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.dub8),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }

}