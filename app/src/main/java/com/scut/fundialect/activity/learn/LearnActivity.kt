package com.scut.fundialect.activity.learn

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.compose.ButtonAppBar
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.VideScreen
import com.scut.fundialect.activity.learn.ui.theme.FunDialectTheme
import com.scut.fundialect.activity.learn.ui.theme.Purple200
import com.scut.fundialect.activity.learn.ui.theme.Purple700
import com.scut.fundialect.activity.learn.ui.theme.white
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.CityHelper.getChildCity
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.database.helper.LearnVideoHelper.getCommitName
import com.scut.fundialect.ui.theme.black
import com.scut.fundialect.ui.theme.blackTransparent


class LearnActivity : BaseComposeActivity() {
    @ExperimentalMaterialApi
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


@ExperimentalMaterialApi
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
                 *
                 *
                 *
                 * 这里是“精选”页面的全部内容，包括一个视频播放器，不包括下面的bar
                 *
                 *
                 *
                 *
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
                 *
                 *
                 *
                 * 这里是“词库”页面的全部内容，不包括下面的bar
                 *
                 *
                 *
                 * **/
                MyWordLibraryPage(context)
            }

        }

    }
}
/**
 *
 *
 *
 * 这里是“词库”页面的全部内容，不包括下面的bar
 *
 *
 *
 * **/
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
    ) { /** Top app bar content */
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

            /**
             *
             * 搜索按钮
             *
             *
             * */
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


/**
 *
 *
 *
 * 这里是“精选”页面的全部内容，包括一个视频播放器，不包括下面的bar
 *
 *
 *
 *
 * **/
@ExperimentalMaterialApi
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

        /**
         *
         *
         *
         * 这个if里面是选择地区，更改地区的向下弹出的菜单*
         *
         *
         *
         * */
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
                    /**
                     *
                     *
                     *
                     * 这个下面的东西代表着每一个单独的写有城市名字的小按钮*
                     *
                     *
                     *
                     * */

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
        val videoInfo=LearnVideoHelper.VideoInfo(1)
        val videoId by remember {
            mutableStateOf(1)
        }
        VideoPlayerWithText(videoInfo,videoId)
    }


}

@ExperimentalMaterialApi
@Composable
private fun VideoPlayerWithText(videoInfo: LearnVideoHelper.VideoInfo,videoId:Int) {
    val videoId1  = videoId


    var uri by remember { mutableStateOf(videoInfo.videoUri) }
    var name by remember { mutableStateOf(videoInfo.videoName) }
    var introduce by remember { mutableStateOf(videoInfo.videoIntroduce) }

    var likeNumber by remember { mutableStateOf(videoInfo.videoLike) }
    var videoUploaderId by remember { mutableStateOf(videoInfo.videoUploaderId) }
    var videoCollect by remember { mutableStateOf(videoInfo.videoCollect) }
    var videoUpdateTime by remember { mutableStateOf(videoInfo.videoUpdateTime) }
    var videoBelongCityId by remember { mutableStateOf(videoInfo.videoBelongCityId) }

    var videoIsLiked by remember { mutableStateOf(videoInfo.videoIsLiked) }
    var videoIsCollect by remember { mutableStateOf(videoInfo.videoIsCollect) }
    var commitNum by remember {
        mutableStateOf(getCommitName(videoId))
    }




    Box(modifier = Modifier.width(videoId1.dp)) {
        if(videoId!=videoId1){
            uri = videoInfo.videoUri
            name =videoInfo.videoName
            introduce=videoInfo.videoIntroduce

            likeNumber=videoInfo.videoLike
            videoUploaderId=videoInfo.videoUploaderId
            videoCollect=videoInfo.videoCollect
            videoUpdateTime=videoInfo.videoUpdateTime
            videoBelongCityId=videoInfo.videoBelongCityId
            videoIsLiked=videoInfo.videoIsLiked
            videoIsCollect=videoInfo.videoIsCollect
            Toast.makeText(context,"视频重组",Toast.LENGTH_SHORT).show()
            commitNum = getCommitName(videoId)

        }
    }


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

        VideScreen(uri)
        /**
         *
         *
         *
         * 这个盒子里面是一系列的漂浮文字*
         *
         *
         *
         * */
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            /**
             * 上面的空白
             * */
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            )
            /**
             * 下面的内容
             * */
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                /**
                 *
                 * 左下角的所有文字和按钮们
                 *
                 * */
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp)
                    .background(Purple700)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        name.forEach { char ->
                            val pinyin = PinyinHelper.convertToPinyinString(
                                char.toString(),
                                ",",
                                PinyinFormat.WITH_TONE_MARK
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                Text(text = pinyin,color = white)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = char.toString(),color = white,modifier = Modifier.background(Purple200))
                            }

                        }
                        Surface(
                            shape = CircleShape,
                            onClick = {
                            TODO("把他收藏了")
                        }) {
                            Image(
                                painter = painterResource(R.drawable.ic_launcher_background) ,
                                contentDescription = ""
                            )
                        }

                    }
                    Text(text = "释义",color = white)
                    Text(text = introduce,color = white)

                }

                /**
                 * 为悬浮按钮让出的空白
                 * */
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(100.dp)
                        .background(white)

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
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp))
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)) {
                    FloatButton("配音",R.drawable.ic_launcher_background,onClick = {
                        TODO("")
                    })
                    FloatButton(likeNumber.toString(),R.drawable.ic_launcher_background,onClick = {
                        TODO("")

                    })
                    FloatButton(commitNum.toString(),R.drawable.ic_launcher_background,onClick = {
                        TODO("")

                    })
                    FloatButton("分享",R.drawable.ic_launcher_background,onClick = {
                        TODO("")

                    })
                }
            }
        }
    }
}


@Composable
fun FloatButton(
    text:String,
    image:Int,
    onClick: (Int) -> Unit,
) {
    TODO("Not yet implemented")
}
