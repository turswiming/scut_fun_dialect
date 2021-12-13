package com.scut.fundialect.activity.myself

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.scut.fundialect.MyApplication
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.video.VideoPlayer2
import com.scut.fundialect.activity.video.VideoPlayerSource
import com.scut.fundialect.activity.video.rememberVideoPlayerController
import com.scut.fundialect.database.helper.ModelVideoHelper
import com.scut.fundialect.database.helper.ModelVideoInfo
import com.scut.fundialect.ui.theme.FontBlack
import com.scut.fundialect.ui.theme.FontWhite

@ExperimentalMaterialApi
@Composable
fun DraftVideoPlayerWithEvent(
    navHostController: NavHostController,
    videoId:Int
){
    Scaffold(
        backgroundColor = Color.Black,
        topBar ={
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .width(80.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentPadding = PaddingValues(
                start = 0.dp,
                end = 0.dp
            )
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "配音",
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.backlight),
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                navHostController.popBackStack()
                            },
                        contentDescription = null
                    )

                }
            }



        }
    }) {
        DraftVideoPlayer(
            navHostController,
            videoId
        )
    }
}
@ExperimentalMaterialApi
@Composable
fun DraftVideoPlayer(
    navHostController: NavHostController,
    videoId:Int
) {
//    SideEffect {
    var videoInfoState by remember {
        mutableStateOf(ModelVideoInfo(videoId))
    }
    Toast.makeText(context,videoInfoState.videoUri,Toast.LENGTH_SHORT).show()
//    var likeNumber by remember { mutableStateOf(videoInfoState.videoLike) }
////    var videoUploaderId by remember { mutableStateOf(videoInfo.videoUploaderId) }
////    var videoCollect by remember { mutableStateOf(videoInfo.videoCollect) }
////    var videoUpdateTime by remember { mutableStateOf(videoInfo.videoUpdateTime) }
////    var videoBelongCityId by remember { mutableStateOf(videoInfo.videoBelongCityId) }
//
//    var videoIsLiked by remember { mutableStateOf(videoInfoState.videoIsLiked) }
//    var videoIsCollect by remember { mutableStateOf(videoInfoState.videoIsCollect) }
//    }

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


    var commitNum by remember {
        mutableStateOf(ModelVideoHelper.getCommitNumber(videoId))
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
    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
        /**
         *
         *
         * 视频播放器，很明显这个东西需要放在最下面，因此不使用任何的排版方式
         *
         *
         * **/
        val videoPlayerController = rememberVideoPlayerController()
        val videoPlayerUiState by videoPlayerController.state.collectAsState()
        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(videoPlayerController, lifecycleOwner) {
            val observer = object : DefaultLifecycleObserver {
                override fun onPause(owner: LifecycleOwner) {
                    videoPlayerController.pause()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
        videoPlayerController.setSource(VideoPlayerSource.Network(videoInfoState.videoUri)
//                Raw(R.raw.video1)
        )
        VideoPlayer2(
            videoPlayerController = videoPlayerController,
            backgroundColor = Color.Transparent,
            modifier = Modifier.fillMaxSize(),
            gesturesEnabled = false,
            controlsEnabled = false,
        )
//        VideScreen(videoInfoState.videoUri)






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

                Text(text = videoInfoState.videoName,color = Color.White,fontSize = 20.sp)
                Text(
                    text = videoInfoState.videoIntroduce,
                    fontSize=14.sp,
                    color = FontWhite,
                    modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp)
                )

            }

        }
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
            modifier = Modifier.fillMaxSize().padding(10.dp)
        ) {

            FloatButton("发布",
                R.drawable.peiyin,
                onClick = {
                    Toast.makeText(context,"视频发布",Toast.LENGTH_SHORT).show()
                    navHostController.navigate("MyselfPage")
                }
            )
        }

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
        Image(
            painter = painterResource(id = image),
            contentScale = ContentScale.Crop,
            contentDescription = text,
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .fillMaxSize()
        )

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
