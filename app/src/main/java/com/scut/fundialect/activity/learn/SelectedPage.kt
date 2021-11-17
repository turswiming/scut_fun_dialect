package com.scut.fundialect.activity.learn

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.scut.fundialect.MyApplication
import com.scut.fundialect.R
import com.scut.fundialect.activity.learn.ui.theme.Purple200
import com.scut.fundialect.activity.publicCompose.VideScreen
import com.scut.fundialect.activity.learn.ui.theme.Purple700
import com.scut.fundialect.activity.learn.ui.theme.white
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.database.helper.LearnVideoHelper.getVideoComment
import com.scut.fundialect.database.helper.LearnVideoHelper.switchCommentLike
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.black
import com.scut.fundialect.ui.theme.blackTransparent
import kotlinx.coroutines.launch


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
fun MySelectedPage(
    showedCityId: List<Int>,
    onStateChange: (key: Int, value: Int) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
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

                    val cityList = CityHelper.getChildCity(1)
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
        val videoInfo= LearnVideoHelper.VideoInfo(1)
        val videoId by remember {
            mutableStateOf(1)
        }

        val state = rememberModalBottomSheetState(
            ModalBottomSheetValue.Hidden
        )
        val scope = rememberCoroutineScope()
        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                //TODO("完成评论页面")
                val videoCommit  = getVideoComment(videoId)
                Column {
                    videoCommit.forEach { it->
                        comment(it)

                    }

                }
            }
        ) {
            VideoPlayerWithText(
                videoInfo = videoInfo,
                videoId = videoId,
                openComment = {
                    scope.launch { state.show() }


                },
                openSharePage = {
//                    sharePageScope.launch {
//                        sharePageState.bottomSheetState.apply {
////                                if (isCollapsed) expand() else collapse()
//                        }
//                    }
                }
            )
//            Column(
//                modifier = Modifier.fillMaxSize().padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("Rest of the UI")
//                Spacer(Modifier.height(20.dp))
//                Button(onClick = { scope.launch { state.show() } }) {
//                    Text("Click to show sheet")
//                }
//            }
        }


    }


}

@Composable
private fun comment(it: LearnVideoHelper.CommentInfo) {
    var isLike by remember {
        mutableStateOf(it.isLiked )
    }
    var numberLiked by remember {
        mutableStateOf(it.numberLiked )
    }
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "头像",
            modifier = Modifier
                /**这个是描边*/
                .border(2.dp, Color.Black, shape = CircleShape)
                .size(30.dp)

        )
        Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.width(150.dp)) {

        }
        Column(
            modifier = Modifier
                .width(40.dp)
                .clickable {
                    //处理compose内ui层逻辑
                    isLike=!isLike
                    if (isLike){
                        numberLiked+1
                    }else{
                        numberLiked-1
                    }
                    switchCommentLike(it.CommitId)
                },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = switch(
                        R.drawable.ic_launcher_background,
                        R.drawable.exo_ic_default_album_image,
                        isLike
                    )
                ),
                contentDescription = "点赞"
            )
            Text(text = numberLiked.toString())
        }

    }

    Text(text = it.comment.toString())
}




@ExperimentalMaterialApi
@Composable
private fun VideoPlayerWithText(
    videoInfo: LearnVideoHelper.VideoInfo,
    videoId:Int,
    openComment:(videoId:Int)->Unit,
    openSharePage:(videoId: Int)->Unit
) {

    var videoId1  = videoId

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
        mutableStateOf(LearnVideoHelper.getCommitNumber(videoId))
    }
    Box(modifier = Modifier.width(videoId.dp)) {
//        if(videoId!=videoId1){
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
        Toast.makeText(MyApplication.context,"视频重组", Toast.LENGTH_SHORT).show()
        commitNum = LearnVideoHelper.getCommitNumber(videoId)

//        }
    }
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
                    .background(Purple700),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start


                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        name.forEach { char ->
                            val pinyin = PinyinHelper.convertToPinyinString(
                                char.toString(),
                                ",",
                                PinyinFormat.WITH_TONE_MARK
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally){
                                Text(
                                    text = pinyin,
                                    color = white,
                                    modifier = Modifier
                                        .width(50.dp),
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Box(modifier = Modifier.size(35.dp)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_launcher_background),
                                        contentDescription = "文字背景")
                                    Text(
                                        text = char.toString(),
                                        color = white,
                                        fontSize = 30.sp
                                    )
                                }

                            }

                        }
                        Surface(
                            shape = CircleShape,
                            modifier = Modifier.size(60.dp),
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
                    //.background(white)

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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier
                    .fillMaxHeight()
                    .width(300.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(200.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    /**
                     *
                     * 配音的按钮。
                     * */

                    FloatButton("配音",R.drawable.ic_launcher_background, onClick = {
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
                        )
                    ) {
                        videoIsLiked = !videoIsLiked
                        LearnVideoHelper.switchLike(videoId)

                    }
                    /**
                     *
                     * 评论的按钮。
                     * */

                    FloatButton(commitNum.toString(),R.drawable.ic_launcher_background, onClick = {
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
        }
    }
}

@Composable
private fun swicherPainter(truePic: Int,falsePic:Int,boolean: Boolean): Painter {
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
            modifier = Modifier.size(60.dp)
        ){
            Image(
                painter = painterResource(id = image) ,
                contentDescription = text,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Text(text = text,color = white)
    }
}
