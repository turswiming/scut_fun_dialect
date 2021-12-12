package com.scut.fundialect.activity.dubing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.scut.fundialect.R
import com.scut.fundialect.activity.video.VideoPlayer3
import com.scut.fundialect.activity.video.VideoPlayerSource
import com.scut.fundialect.activity.video.rememberVideoPlayerController
import com.scut.fundialect.help.GetRandomSentence

@Composable
fun InDubbingWithEvent(navController: NavHostController){
    InDubbingPage()
}
@Composable
fun InDubbingPreview(){

}
@Composable
@Preview
fun InDubbingPage(){
    Image(
        painter = painterResource(id =R.drawable.dubingbackground) ,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()
    )
    Scaffold(
        drawerBackgroundColor = Color.Transparent,
        backgroundColor = Color.Transparent,
//        modifier = Modifier.size(200.dp,800.dp),
        topBar = {
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
//                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center
                        )
                    }

                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.backlight),
                            modifier = Modifier.size(25.dp),
                            contentDescription = null
                        )

                    }
                }



            }
            
        }
    ) {
        Image(
            painter = painterResource(id =R.drawable.dubingbackground) ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
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

            //TODO: 2021/12/8 对视频源进行延迟赋值。
            videoPlayerController.setSource(VideoPlayerSource.Raw(R.raw.video1))
            /**
             * 这个的高度是300dp,需要点开到videoplayer3里面再改
             * */
            VideoPlayer3(
                onSeek={
//                    onSeek(it)
                },
                onSeekStopped={
//                    onSeekStopped(it)

                },
                videoPlayerController = videoPlayerController,
                backgroundColor = Color.Transparent,
                modifier = Modifier.fillMaxSize(),
                gesturesEnabled = true,
                controlsEnabled = true,
            )
            var fixedSizeArr = arrayOfNulls<String>(15)
            SideEffect {
                var pointer =0
                fixedSizeArr.forEach {
                    fixedSizeArr[pointer] = GetRandomSentence.randomSentence()
                    pointer++
                }
            }
            var lazyListState =  rememberLazyListState()
//            lazyListState.animateScrollToItem(0,0)
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment =Alignment.CenterHorizontally,
                state = lazyListState,
                content = {
                    items(fixedSizeArr.size) {index->
                        fixedSizeArr[index]?.let {
                                it1 -> Text(text = it1,color= Color.White)
                        }
                    }
                }
            )


        }



    }
}