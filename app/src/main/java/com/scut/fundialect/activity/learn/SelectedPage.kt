package com.scut.fundialect.activity.learn

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.publicCompose.ShareButton
import com.scut.fundialect.activity.publicCompose.VideoPlayerWithText
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.database.helper.LearnVideoHelper.getVideoComment
import com.scut.fundialect.database.helper.LearnVideoHelper.switchCommentLike
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.BackgroundGray
import com.scut.fundialect.ui.theme.BackgroundLightGrey
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
            /**
             *
             * 这里是推荐页面中第二排的选择框，包括了“推荐 按钮，添加城市等按钮。
             *
             * */
            TabRow(
                selectedTabIndex = cityStateNow,
                modifier= Modifier
                    .background(Color.Black)
                    .height(40.dp),

                ) {
                Tab(
                    text = { Text("推荐") },
                    modifier= Modifier
                        .background(Color.Black)
                        .padding(0.dp),
                    selected = cityStateNow == 0,
                    onClick = { cityStateNow = 0 }
                )
                showedCityId.forEachIndexed { index, cityId ->
                    Tab(
                        modifier= Modifier
                            .background(Color.Black)
                            .padding(0.dp),
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
                    modifier= Modifier
                        .background(Color.Black)
                        .padding(0.dp),
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
                        modifier= Modifier
                            .background(Color.Black)
                            .padding(0.dp)
                            .height(20.dp)
                            .width(20.dp),
                        painter = painterResource(id = R.drawable.pluswhite),
                        contentDescription = "加号")
                }
            }
            //LazyColumn(content = )

        }



        Box(modifier = Modifier.fillMaxWidth()){


            val videoInfo= LearnVideoHelper.VideoInfo(3)
            val videoId by remember {
                mutableStateOf(3)
            }

            val state = rememberModalBottomSheetState(
                ModalBottomSheetValue.Hidden
            )
            val scope = rememberCoroutineScope()
            val shareState = rememberModalBottomSheetState(
                ModalBottomSheetValue.Hidden
            )
            val shareScope = rememberCoroutineScope()
            /**
             *
             * 这里是包括了完整的视频播放器内容，也包括了评论页面和分享页面。
             *
             * */
            ModalBottomSheetLayout(

                sheetState = shareState,
                /**
                 *
                 * 这里是分享弹出框。
                 *
                 * */
                sheetContent = {
                    Column(Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)) {
                            Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
                                Text(
                                    text = "分享至",
                                    fontSize = 20.sp,)
                            }
                            Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.CenterEnd){
                                Image(
                                    painter = painterResource(id = R.drawable.close_black),
                                    contentDescription = "关闭分享",
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp)
                                        .clickable {
                                        shareScope.launch { shareState.hide() }
                                    })
                            }
                        }
                        Row(
                            Modifier.height(80.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(5.dp))
                            ShareButton(
                                "微信好友",
                                R.drawable.wx_friend,
                                onClick = {
                                    Toast.makeText(context,"分享给了微信好友",Toast.LENGTH_SHORT).show()
                                }
                            )
                            ShareButton(
                                "微信朋友圈",
                                R.drawable.wx_public,
                                onClick = {
                                    Toast.makeText(context,"分享给了微信朋友圈",Toast.LENGTH_SHORT).show()
                                }
                            )
                            ShareButton(
                                "QQ好友",
                                R.drawable.qq_friend,
                                onClick = {
                                    Toast.makeText(context,"分享给了QQ好友",Toast.LENGTH_SHORT).show()
                                }
                            )
                            ShareButton(
                                "QQ空间",
                                R.drawable.qq_public,
                                onClick = {
                                    Toast.makeText(context,"分享给了QQ空间",Toast.LENGTH_SHORT).show()
                                }
                            )
                            ShareButton(
                                "今日头条",
                                R.drawable.today_news,
                                onClick = {
                                    Toast.makeText(context,"分享给了今日头条",Toast.LENGTH_SHORT).show()
                                }
                            )
                            ShareButton(
                                "微博",
                                R.drawable.wb_news,
                                onClick = {
                                    Toast.makeText(context,"分享给了微博",Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                        Row(
                            Modifier.height(80.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ShareButton(
                                "保存本地",
                                R.drawable.download_local,
                                onClick = {
                                    Toast.makeText(context,"已保存本地",Toast.LENGTH_SHORT).show()
                                }
                            )
                            ShareButton(
                                "复制链接",
                                R.drawable.copy_address,
                                onClick = {
                                    Toast.makeText(context,"已复制链接",Toast.LENGTH_SHORT).show()
                                }
                            )
                            //下面我复制了四个空盒子用来做占位
                            val list = listOf(1,2,3,4)
                            list.forEach { _ ->
                                Box(
                                    modifier = Modifier
                                        .height(75.dp)
                                        .width(65.dp),
                                ) {

                                }

                            }


                        }

                    }
                }
            ) {
                ModalBottomSheetLayout(

                    sheetState = state,
                    /**
                     *
                     * 这里是评论弹出框。
                     *
                     * */
                    sheetContent = {

                        val videoCommit  = getVideoComment(videoId)
                        LazyColumn(

                        ){
                            items(videoCommit.size){index->
                                /**
                                 *
                                 * 这里是 每一个 评论。
                                 *
                                 * */
                                Comment(it = videoCommit[index])
                            }
                        }

                    }
                ) {
                    /**这里是完整的视频播放器，包括了悬浮按钮，介绍文字等
                     * */
                    VideoPlayerWithText(
                        videoInfo = videoInfo,
                        videoId = videoId,
                        openComment = {
                            scope.launch {
                                state.show()
                            }


                        },
                        openSharePage = {
                            shareScope.launch {
                                shareState.show()
                            }
                        }
                    )

                }

            }

            Surface(
                modifier = Modifier
                    .height(switch(200.dp, 1.dp, cityStateNow == 5))
                    .fillMaxWidth()
                    .background(Color.Black),
                shape = RoundedCornerShape (0. dp,0.dp,25.dp,25.dp),
                color = BackgroundGray,
                elevation=10.dp
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
//                        Box(
//                            modifier = Modifier
//                                .clickable {
//
//                                }
//                                .width(64.dp)
//                                .height(32.dp),
//
//
//                            ) {
                            Text(
                                text = AnnotatedString(cityList[index].getTheName()),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        cityStateNow = cityStateLast

                                        onStateChange(cityStateLast, index)

                                    }
                                    .width(64.dp)
                                    .height(32.dp),



                            )
//                        }


                    }
                }


            }
        }
    }


}
/**
 *
 * 这里是每一个评论。
 *
 * */
@Composable
private fun Comment(it: LearnVideoHelper.CommentInfo) {
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
            .padding(2.dp,5.dp),
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
        modifier = Modifier.width(200.dp).height(80.dp)) {
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
                    switchCommentLike(it.CommitId)
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


