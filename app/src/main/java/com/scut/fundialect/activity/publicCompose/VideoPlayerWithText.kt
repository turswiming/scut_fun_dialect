package com.scut.fundialect.activity.publicCompose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.scut.fundialect.MyApplication
import com.scut.fundialect.R
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.FontBlack
import com.scut.fundialect.ui.theme.FontWhite
import com.scut.fundialect.ui.theme.MainColor
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@Composable
fun VideoPlayerWithText(
    videoInfo: LearnVideoHelper.VideoInfo,
    videoId:Int,
    openComment:(videoId:Int)->Unit,
    openSharePage:(videoId: Int)->Unit
) {
    Toast.makeText(MyApplication.context,"视频重组", Toast.LENGTH_SHORT).show()
    var videoInfoState by remember {
        mutableStateOf(videoInfo)
    }
    /**
     *
     * 虽然这些注释没啥用但是别删，这里是非常珍贵的弯路史料
     *
     * */
    //var videoId1  = videoId
//    var videoIdBackup by remember { mutableStateOf(1) }
//
//    var uri by remember { mutableStateOf(videoInfo.videoUri) }
//    var name by remember { mutableStateOf(videoInfo.videoName) }
//    var introduce by remember { mutableStateOf(videoInfo.videoIntroduce) }
//
    var likeNumber by remember { mutableStateOf(videoInfoState.videoLike) }
//    var videoUploaderId by remember { mutableStateOf(videoInfo.videoUploaderId) }
//    var videoCollect by remember { mutableStateOf(videoInfo.videoCollect) }
//    var videoUpdateTime by remember { mutableStateOf(videoInfo.videoUpdateTime) }
//    var videoBelongCityId by remember { mutableStateOf(videoInfo.videoBelongCityId) }

    var videoIsLiked by remember { mutableStateOf(videoInfoState.videoIsLiked) }
    var videoIsCollect by remember { mutableStateOf(videoInfoState.videoIsCollect) }
    var commitNum by remember {
        mutableStateOf(LearnVideoHelper.getCommitNumber(videoId))
    }
//    Box(modifier = Modifier.width(videoId.dp)) {
//        val scope = rememberCoroutineScope()
//        if(videoId!=videoIdBackup){
//            videoIdBackup = videoId
//            scope.launch {
//                uri = videoInfo.videoUri
//                name =videoInfo.videoName
//                introduce=videoInfo.videoIntroduce
//
//                likeNumber=videoInfo.videoLike
//                videoUploaderId=videoInfo.videoUploaderId
//                videoCollect=videoInfo.videoCollect
//                videoUpdateTime=videoInfo.videoUpdateTime
//                videoBelongCityId=videoInfo.videoBelongCityId
//                videoIsLiked=videoInfo.videoIsLiked
//                videoIsCollect=videoInfo.videoIsCollect
//                commitNum = LearnVideoHelper.getCommitNumber(videoId)
//
//            }
//        }
//
//    }
    //var videoIdOld = videoId1
    /**
     *
     *
     *
     * 这个盒子里面是视频播放器和一系列的漂浮文字*
     *
     *
     *
     * */
    Box(modifier = Modifier.fillMaxSize()) {
        /**
         *
         *
         * 视频播放器，很明显这个东西需要放在最下面，因此不使用任何的排版方式
         *
         *
         * **/

        VideScreen(videoInfoState.videoUri)


//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween
//
//        ) {
//
//
//            /**
//             * 上面的空白
//             * */
//
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp),
//            )
//            /**
//             * 下面的内容
//             * */

        /**
         *
         *
         *
         * 这个盒子里面是一系列的漂浮文字*
         *
         *
         *
         * */
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                Alignment.BottomStart

            ) {
                /**
                 *
                 * 左下角的所有文字和按钮们
                 *
                 * */
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(40.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                    //.background(white)
                ){}
                Column(modifier = Modifier
                    .width(300.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        videoInfoState.videoName.forEach { char ->
                            /**
                             * 這裏是每一個文字
                             * */
                            val pinyin = PinyinHelper.convertToPinyinString(
                                char.toString(),
                                ",",
                                PinyinFormat.WITH_TONE_MARK
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally,){
                                Text(
                                    text = pinyin,
                                    fontSize=14.sp,
                                    color = FontWhite,
                                    modifier = Modifier
                                        .width(50.dp),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Box(modifier = Modifier.size(35.dp)) {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.ic_launcher_background),
//                                        contentDescription = "文字背景")
                                    Text(
                                        text = char.toString(),
                                        color = FontWhite,
                                        fontSize = 30.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }

                            }

                        }
                        Surface(
                            shape = CircleShape,
                            modifier = Modifier.size(28.dp),
                            onClick = {

                                videoIsCollect =!videoIsCollect
                                LearnVideoHelper.switchCollect(videoId)
                            }) {
                            Image(
                                painter = swicherPainter(
                                    R.drawable.ic_launcher_foreground,
                                    R.drawable.ic_launcher_background,
                                    videoIsCollect),
                                contentDescription = ""
                            )
                        }

                    }
                    Text(text = "释义",color = FontWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(10.dp))
                    Text(
                        text = videoInfoState.videoIntroduce,
                        fontSize=14.sp,
                        color = FontWhite,
                        modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
                    )

                }

                /**
                 * 为悬浮按钮让出的空白
                 * */


                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                    //.background(white)
                )
            }
//        }
        /**
         *
         *
         *
         * 这个盒子里面是一系列的漂浮按鈕*
         *
         *
         *
         * */
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier.fillMaxSize()
        ) {


//        Column(modifier = Modifier.fillMaxSize()) {
//            Spacer(modifier = Modifier
//                .height(200.dp)
//                .fillMaxWidth()
//            )
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Spacer(modifier = Modifier
//                    .fillMaxHeight()
//                    .width(300.dp))
            Column(
                modifier = Modifier
                    .height(300.dp)
                    .width(100.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                /**
                 *
                 * 配音的按钮。
                 * */

                FloatButton("配音", R.drawable.ic_launcher_background, onClick = {
                    TODO("")
                })
                /**
                 * 下面的是点赞的按钮。
                 *
                 * 两个图片各自是点赞和没有点赞时候显示的内容
                 * */

                FloatButton(likeNumber.toString(),
                    image = switch(
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_foreground,
                        videoIsLiked
                    ),
                    onClick = {
                        videoIsLiked = !videoIsLiked
                        likeNumber += if (videoIsLiked) 1 else -1
                        LearnVideoHelper.switchLike(videoId)

                    }

                )
                /**
                 *
                 * 评论的按钮。
                 * */
                FloatButton(commitNum.toString(), R.drawable.ic_launcher_background, onClick = {
                    openComment(videoId)
                })
                /**
                 *
                 * 分享的按钮。
                 * */
                FloatButton("分享", R.drawable.ic_launcher_background, onClick = {
                    openSharePage(videoId)
                })
            }
        }
//            }
//        }
    }
}

@Composable
fun swicherPainter(truePic: Int, falsePic:Int, boolean: Boolean): Painter {
    if (boolean){
        return painterResource(id = truePic)
    }else{
        return painterResource(id = falsePic)
    }
}


@Composable
fun FloatButton(
    text:String,
    image: Int,
    onClick: () -> Unit,
) {
    Column(
        Modifier
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(40.dp)
        ){
            Image(
                painter = painterResource(id = image) ,
                contentDescription = text,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Text(text = text,color = FontWhite)
    }
}
@Composable
fun ShareButton(
    text:String,
    image: Int,
    onClick: () -> Unit,
) {
    Column(
        Modifier
            .width(65.dp)
            .height(75.dp)
            .clickable {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(50.dp)
        ){
            Image(
                painter = painterResource(id = image) ,
                contentDescription = text,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Text(text = text,color = FontBlack)
    }
}
