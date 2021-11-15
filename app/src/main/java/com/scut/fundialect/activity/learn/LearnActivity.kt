package com.scut.fundialect.activity.learn

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.scut.fundialect.MyApplication
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.learn.ui.theme.FunDialectTheme
import com.scut.fundialect.activity.learn.ui.theme.white
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.CityHelper.getChildCity
import com.scut.fundialect.ui.theme.black
import com.scut.fundialect.ui.theme.blackTransparent
import java.io.File

class LearnActivity : BaseComposeActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainPage(this)
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
private fun MainPage(context: Context) {

    var state1  by remember { mutableStateOf(0) }
    var showedPage1 = remember {
        mutableStateListOf(2,3,4,5)
    }
    val titles = listOf("精选", "词库")
    Scaffold(
        topBar = {
            /**
             * 最上面的bar，包括精选和词库
             * **/
            MyTopAppBar(state1, titles,onStateChange = {state1 = it })

        }
    ) {
        // Screen content
        Row {
            //Text(text = "${state1.toString()}")
            if(state1==0){
                /**
                 * 这里是“精选”页面的全部内容，包括一个视频播放器，不包括下面的bar
                 * **/
                MySelectedPage(showedPage1,
                    onStateChange = {
                            k, value ->
                    if(k!=0){
                        showedPage1[k-1] = value+2
                        //Toast.makeText(context,"key\t$k\nvalue\t$value",Toast.LENGTH_SHORT).show()
                    }
                    })
            }else{
                /**
                 * 这里是“词库”页面的全部内容，不包括下面的bar
                 * **/
                MyWordLibraryPage(context)
            }

        }

    }
}

@Composable
fun MyWordLibraryPage(context: Context) {
    Scaffold(
        bottomBar =  {
            MyButtonAppBar(onStateChange = {}, context = context,0)

        }
    ){

    }
}

@Composable
private fun MyTopAppBar(state1: Int, titles: List<String>, onStateChange: (Int) -> Unit) {
    var state11 = state1
    //界面最上面的选择框框，包括精选和词库
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary
    ) { /* Top app bar content */
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "logo"
            )


            // Screen content
            TabRow(
                selectedTabIndex = state11,
                Modifier.width(200.dp)
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = state11 == index,
                        onClick = {
                            state11 = index
                            onStateChange(index)
                        }
                    )
                }
            }

            //搜索按钮
            Button(
                onClick = { },
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp
                )
            ) {
                //Text(text = "${state11.toString()}")

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "search icon"
                )
            }
        }

    }
}



@ExperimentalFoundationApi
@Composable
private fun MySelectedPage(
    showedCityId: List<Int>,
    onStateChange: (key: Int, value: Int) -> Unit
) {
    Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
        var cityStateNow by remember { mutableStateOf(0) }
        var cityStateLast  by remember { mutableStateOf(0) }
        // Screen content
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(
                selectedTabIndex = cityStateNow,

                ) {
                Tab(
                    text = { Text("推荐") },
                    selected = cityStateNow == 0,
                    onClick = { cityStateNow = 0 }
                )
                showedCityId.forEachIndexed { index, cityId ->
                    Tab(
                        text = {
                            Text(CityHelper.getCityName(cityId)+"话")
                        },
                        selected = cityStateNow == index+1,
                        onClick = { cityStateLast=cityStateNow
                            cityStateNow = index+1

                        }
                    )

                }
                Tab(
                    selected = cityStateNow == 5,
                    onClick = {
                        /**
                         * 如果之前选中的不是第五个按钮：
                         *      -那就选中第五个按钮
                         * 如果之前选中了第五个按钮
                         *      -那就选中第五个按钮之前的按钮
                         * **/
                        if(cityStateNow != 5){
                            cityStateLast = cityStateNow
                            cityStateNow = 5
                        }else{
                            cityStateNow = cityStateLast
                        }
                    }
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "加号")
                }
            }
            //LazyColumn(content = )

        }
        if(cityStateNow == 5){
            Surface(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape (0. dp,0.dp,25.dp,25.dp),
                color = black
            ) {
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(minSize = 64.dp)
                ) {
                    val cityList = getChildCity(1)
                    items(cityList.size) { index ->
                        Box(
                            modifier = Modifier
                                .clickable {
                                    //Toast.makeText(context,"key\t$cityStateLast\nvalue\t$index",Toast.LENGTH_SHORT).show()

                                    onStateChange(cityStateLast, index)
                                }
                                .width(64.dp)
                                .height(32.dp),


                            ) {
                            Text(
                                text = AnnotatedString(cityList[index].getTheName()),
                                color = white,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()


                            )
                        }


                    }
                }


            }
            Box(modifier = Modifier
                .fillMaxSize()
                .background(blackTransparent)) {

            }

        }
        //这个盒子里面是视频播放器和一系列的漂浮文字
        Box(modifier = Modifier.fillMaxSize()) {
            VideScreen()
        }
    }


}

@Composable
fun VideScreen (){
    val context = LocalContext.current
    val exoPlayer = SimpleExoPlayer.Builder(context)
        .build()
        .apply {
            playWhenReady = false
        }
    //uri可以时网络url资源，这里我adb push了一个视频到使用sd卡根目录
    val mediaItem = MediaItem.fromUri(Uri.parse("android.resource://"+ context.packageName +"/"+R.raw.video1))
    exoPlayer.setMediaItem(mediaItem)
    exoPlayer.prepare()
    exoPlayer.play()
    PlayerSurface(modifier = Modifier.width(400.dp).height(400.dp)){
        it.player = exoPlayer
    }
}

@Composable
fun PlayerSurface(
    modifier: Modifier,
    onPlayerViewAvailable: (PlayerView) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                useController = true
                onPlayerViewAvailable(this)
            }
        },
        modifier = modifier
    )
}
