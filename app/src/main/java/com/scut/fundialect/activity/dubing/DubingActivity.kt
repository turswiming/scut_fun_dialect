package com.scut.fundialect.activity.dubing

//import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.scut.fundialect.R
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.ModelVideoHelper
import com.scut.fundialect.database.helper.ModelVideoHelper.getCollectedModelVideo
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.help.switch
import com.scut.fundialect.help.toDateStr
import com.scut.fundialect.ui.theme.CustomOrange
import com.scut.fundialect.ui.theme.FontWhite
import com.scut.fundialect.ui.theme.SecondNavColor
import com.scut.fundialect.ui.theme.Transparent
import kotlinx.coroutines.delay

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun dubbingPageMainAll(
    navController: NavHostController,
    context: Context
) {
    DubingPageWithEvent(
        initPageIndex=2,
        R.drawable.dubbackground,

        navController,
        context,
        dubPageContent = {
            DubMainPage(navController)
        },
        barContent = {
            /**
             * 左上角的俩大字
             *
             * */
            Text(text = "配音", fontSize = 24.sp, color = Color.White,fontWeight = FontWeight.Bold,modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp))
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
                        navController.navigate("SearchPage/${2}")

                    }

            )
        }
    )
}
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun DubingPageWithEvent(
    initPageIndex:Int =2,
    imageId:Int = R.drawable.dubbackground,
    navController: NavHostController,
    context: Context,
    dubPageContent:@Composable () -> Unit,
    barContent:@Composable () -> Unit

) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
            painter = painterResource(id = imageId), contentDescription = null)
        var showedCityId = remember {
            mutableStateListOf(2,3,4,5)
        }
        var cityStateNow by remember { mutableStateOf(0) }
        var cityStateLast  by remember { mutableStateOf(0) }
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                /**
                 *
                 * 这里面是一整个上面的bar，包括搜索按钮和选择城市的按钮
                 *
                 * */
//            DubingTopAppBar(cityStateNow,
//                cityStateLast,
//                navController = navController,
//                onSelectPageChange = {
//                }
//            )


                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(77.dp),
                    backgroundColor = Transparent,
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = Transparent),

                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            barContent()

                        }
                        /**
                         * bar下面选择城市的东西
                         *
                         * */


                        /**
                         *
                         * 这里是推荐页面中第二排的选择框，包括了“推荐 按钮，添加城市等按钮。
                         *
                         * */
                        TabRow(
                            selectedTabIndex = cityStateNow,
                            contentColor = CustomOrange,
                            modifier = Modifier.background(color = Transparent),
                            backgroundColor = Transparent


                        ) {
                            Tab(
                                text = { Text("推荐",color = Color.Black) },
                                selected = cityStateNow == 0,
                                onClick = { cityStateNow = 0 }
                            )
                            showedCityId.forEachIndexed { index, cityId ->
                                Tab(
                                    text = {
                                        Text(CityHelper.getCityName(cityId),color = Color.Black)
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
                                Column {
                                    Spacer(modifier = Modifier.height(17.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.plusblack),
                                        contentDescription = "加号",
                                        modifier = Modifier.size(15.dp)
                                    )
                                }

                            }
                        }
//            var (cityStateNow, cityStateLast) = pair(ColorMode.dark,showedCityId)


                    }
                }
            },
            bottomBar ={
                MyButtonAppBar(
                    colorMode = ColorMode.light,
                    gotoAnotherActivity = {gotoAnotherActivity(navController,it)},
                    onStateChange = {

                    },
                    initPageIndex = initPageIndex
                )
            }

        ) {
            dubPageContent()
            if (cityStateNow == 5) {
                CitySelectPage(onselectCityChange = {
                    if (cityStateLast != 0) {
                        showedCityId[cityStateLast - 1] = it + 2
                    }
//            showedCityId[cityStateLast] =it
                })
            }
        }
    }
//    var showCitySelectPage by remember { mutableStateOf(false)}
    
}

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun DubingPageWithEvent2(
    initPageIndex:Int =2,
    imageId:Int = R.drawable.dubbackground,
    navController: NavHostController,
    context: Context,
    dubPageContent:@Composable () -> Unit,
    barContent:@Composable () -> Unit

) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
            painter = painterResource(id = imageId), contentDescription = null)
        var showedCityId = remember {
            mutableStateListOf(2,3,4,5)
        }
        var cityStateNow by remember { mutableStateOf(0) }
        var cityStateLast  by remember { mutableStateOf(0) }
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                /**
                 *
                 * 这里面是一整个上面的bar，包括搜索按钮和选择城市的按钮
                 *
                 * */
//            DubingTopAppBar(cityStateNow,
//                cityStateLast,
//                navController = navController,
//                onSelectPageChange = {
//                }
//            )


                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(77.dp),
                    backgroundColor = Transparent,
                    elevation = 0.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = Transparent),

                        ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            barContent()

                        }
//            var (cityStateNow, cityStateLast) = pair(ColorMode.dark,showedCityId)


                    }
                }
            },
            bottomBar ={
                MyButtonAppBar(
                    colorMode = ColorMode.light,
                    gotoAnotherActivity = {gotoAnotherActivity(navController,it)},
                    onStateChange = {

                    },
                    initPageIndex = initPageIndex
                )
            }

        ) {
            dubPageContent()

        }
    }
//    var showCitySelectPage by remember { mutableStateOf(false)}

}
@ExperimentalFoundationApi
@Composable
private fun CitySelectPage(onselectCityChange:(CityId: Int)->Unit) {
    Surface(
        modifier = Modifier
            .height(215.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp),
        color = Color.White
    ) {
        Spacer(modifier = Modifier.height(15.dp))
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
                            onselectCityChange(index - 2)
                        }
                        .width(64.dp)
                        .height(32.dp),


                    ) {
                    Text(
                        text = AnnotatedString(cityList[index].getTheName()),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()


                    )
                }


            }
        }


    }
}

@ExperimentalPagerApi
@Composable
fun DubMainPage(navController:NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Surface(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(300.dp),
            elevation = 5.dp,
            color = Color.White
        ) {
            /**这里是上面的视频banner，包括白色的方盒子和下面的文字*/
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {
//                var modelVideos:List<ModelVideoHelper.ModelVideoInfo>
                val modelVideos = getCollectedModelVideo()


                val pagerState = rememberPagerState(
                    //总页数
                    pageCount = modelVideos.size,
                    //预加载的个数
                    initialOffscreenLimit = 1,
                    //是否无限循环
                    infiniteLoop = true,
                    //初始页面
                    initialPage = 0
                )
                /**这里是上面的视频略缩图banner，不包括文字*/
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                ) { page ->
                    //自动滚动
                    LaunchedEffect(pagerState.currentPage) {
                        if (pagerState.pageCount > 0) {
                            delay(2000)
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = rememberImagePainter(modelVideos[page].videoPicUri),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }
                }
                /**这里是上面的视频略缩图banner中，下面的三个圆点，不包括下面的文字*/
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        color = switch(
                            CustomOrange,
                            SecondNavColor,
                            pagerState.currentPage == 0
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .width(
                                switch(
                                    40.dp,
                                    10.dp,
                                    pagerState.currentPage == 0
                                )
                            )
                            .height(10.dp),
                        elevation = 0.dp,
                        border = null,
                    ) {}
                    Spacer(modifier = Modifier.width(5.dp))
                    Surface(
                        color = switch(
                            CustomOrange,
                            SecondNavColor,
                            pagerState.currentPage == 1
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .width(
                                switch(
                                    40.dp,
                                    10.dp,
                                    pagerState.currentPage == 1
                                )
                            )
                            .height(10.dp),
                        elevation = 0.dp,
                        border = null,
                    ) {}
                    Spacer(modifier = Modifier.width(5.dp))
                    Surface(
                        color = switch(
                            CustomOrange,
                            SecondNavColor,
                            pagerState.currentPage == 2
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .width(
                                switch(
                                    40.dp,
                                    10.dp,
                                    pagerState.currentPage == 2
                                )
                            )
                            .height(10.dp),
                        elevation = 0.dp,
                        border = null,
                    ) {}


                }
                /**这里是上面的视频略缩图下面的文字，包括视频名称，上传时间等，还有一个按钮表达以此开始配音*/
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = modelVideos[pagerState.currentPage].videoName,
                            fontSize = 14.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        UserHelpr.UserInfo(
                                            modelVideos[pagerState.currentPage].videoUploaderId
                                        ).userPicFile
                                    ),
                                    contentDescription = "上传者头像",
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                            Text(text = UserHelpr.UserInfo(modelVideos[pagerState.currentPage].videoUploaderId).userNickName)
                            Spacer(modifier = Modifier.width(15.dp))

                            Text(
                                text = modelVideos[pagerState.currentPage].videoUpdateTime.toLong()
                                    .toDateStr("yyyy-MM-dd")
                            )
                            Spacer(modifier = Modifier.width(15.dp))

                            Image(
                                painter = painterResource(id = R.drawable.topic2),
                                contentDescription = "爱心",
                                modifier = Modifier.size(15.dp)

                            )
                            Text(
                                text = modelVideos[pagerState.currentPage].videoLike.toString()
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.dub1),
                        contentDescription = "前往配音",
                        modifier = Modifier.size(50.dp)
                    )
                }

            }
        }
        /**
         * 热门推荐 文字和按钮
         * */
        Row(horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth()) {
            Text(text = "热门推荐")
            Row(Modifier.clickable {
                navController.navigate("HotSuggested")
            }
            ) {
                Text(text = "查看更多")
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    Modifier
                        .rotate(180F)
                        .size(20.dp),
                )
            }

        }
        val modelVideos = getCollectedModelVideo()

        /**
         * 热门推荐 内容
         * */
        HorizontalPicShower(modelVideos,navHostController = navController)
            /**
             * 查看更多 文字和按钮
             * */
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            Text(text = "他人作品")
            Row(Modifier.clickable {
                navController.navigate("OtherWorks")
            }) {
                Text(text = "查看更多")
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                    Modifier
                        .rotate(180F)
                        .size(20.dp),
                )
            }
        }
        /**
         * 查看更多 内容
         * */
        val modelVideos2 = getCollectedModelVideo()
        VerticalPicShower(modelVideos2,navController)
    }
}

@Composable
fun VerticalPicShower(modelVideos2: List<ModelVideoHelper.ModelVideoCathe>, navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        modelVideos2.forEach {
            MyPictureShower(it,navHostController = navController,clickable = {})

        }
    }
}

@Composable
fun HorizontalPicShower(
    modelVideos: List<ModelVideoHelper.ModelVideoCathe>,
    navHostController: NavHostController,
    clickable:(Int)->Unit={
        navHostController.navigate("InDubbingPage/${it}")
    },
    ImageSelected:Int=R.drawable.dub2,
    ImageUnSelected:Int=R.drawable.dub3,


    ) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(15.dp)
    ) {
        modelVideos.forEach {
            var isCollect by remember {
                mutableStateOf(it.videoIsCollect)
            }
            Surface(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(120.dp)
                    .clickable {
                        clickable(it.videoId)
                    }
            ) {
                Image(
                    painter = rememberImagePainter(it.videoPicUri),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                ) {
                    Image(
                        painter = painterResource(
                            id = switch(
                                ImageSelected,
                                ImageUnSelected,
                                isCollect
                            )
                        ),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                isCollect = !isCollect
                                ModelVideoHelper.switchCollect(it.videoId)
                            },

                        contentDescription = "收藏按钮"
                    )
                }
                Box(contentAlignment = Alignment.BottomStart, modifier = Modifier.fillMaxSize()) {
                    Column() {
                        Text(text = it.videoName, color = FontWhite)
                        val pinyin = PinyinHelper.convertToPinyinString(
                            it.videoName,
                            "",
                            PinyinFormat.WITHOUT_TONE
                        )
                        Text(text = pinyin, color = FontWhite)
                    }
                }
            }

        }
    }
}

@Composable
fun MyPictureShower(
    it: ModelVideoHelper.ModelVideoCathe,
    navHostController: NavHostController,
    clickable:()->Unit,
    ImageSelected:Int=R.drawable.dub2,
    ImageUnSelected:Int=R.drawable.dub3,

    ) {
    var isCollect by remember {
        mutableStateOf(it.videoIsCollect)
    }
    Surface(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .padding(15.dp, 10.dp)
            .clickable {
                clickable()
            }
    ) {
        Image(
            painter = rememberImagePainter(it.videoPicUri),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),

        ) {
            Image(
                painter = painterResource(
                    id = switch(
                        ImageSelected,
                        ImageUnSelected,
                        isCollect
                    )
                ),
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        isCollect != isCollect
                        ModelVideoHelper.switchCollect(it.videoId)
                    },
                contentDescription = "收藏按钮"
            )
        }
    }
}


///**
// *
// * 这里面是一整个上面的bar，包括搜索按钮和选择城市的按钮
// *
// * */
//@Composable
//fun DubingTopAppBar(cityStateNow:Int,cityStateLast:Int,onSelectPageChange:()->Unit,navController:NavHostController
//) {
//    TopAppBar(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp),
//        backgroundColor = Transparent,
//        elevation = 0.dp
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .background(color = Transparent),
//
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween,
//            ) {
//                /**
//                 * 左上角的俩大字
//                 *
//                 * */
//                Text(text = "配音")
//                /**
//                 *
//                 * 搜索按钮
//                 *
//                 *
//                 * */
//                Image(
//                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                    contentDescription = "search icon",
//                    modifier = Modifier.clickable {
//                        goToSearchPage(navController)
//
//                    }
//
//                )
//            }
//            /**
//             * bar下面选择城市的东西
//             *
//             * */
//
//            var showedCityId = remember {
//                mutableStateListOf(2,3,4,5)
//            }
//            /**
//             *
//             * 这里是推荐页面中第二排的选择框，包括了“推荐 按钮，添加城市等按钮。
//             *
//             * */
//            TabRow(
//                selectedTabIndex = cityStateNow,
//                contentColor = CustomOrange,
//                modifier = Modifier.background(color = Transparent),
//                backgroundColor = Transparent
//
//
//                ) {
//                Tab(
//                    text = { Text("推荐") },
//                    selected = cityStateNow == 0,
//                    onClick = { cityStateNow = 0 }
//                )
//                showedCityId.forEachIndexed { index, cityId ->
//                    Tab(
//                        text = {
//                            Text(CityHelper.getCityName(cityId)+"话")
//                        },
//                        selected = cityStateNow == index+1,
//                        onClick = { cityStateLast=cityStateNow
//                            cityStateNow = index+1
//
//                        }
//                    )
//
//                }
//                Tab(
//                    selected = cityStateNow == 5,
//                    onClick = {
//                        /**
//                         * 如果之前选中的不是第五个按钮：
//                         *      -那就选中第五个按钮
//                         * 如果之前选中了第五个按钮
//                         *      -那就选中第五个按钮之前的按钮
//                         * **/
//
//                        if(cityStateNow != 5){
//                            cityStateLast = cityStateNow
//                            cityStateNow = 5
//                        }else{
//                            cityStateNow = cityStateLast
//                        }
//                    }
//                ){
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_launcher_background),
//                        contentDescription = "加号")
//                }
//            }
////            var (cityStateNow, cityStateLast) = pair(ColorMode.dark,showedCityId)
//
//
//        }
//    }
//}

//@OptIn(ExperimentalFoundationApi::class)
//@ExperimentalFoundationApi
//@ExperimentalPagerApi
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeTutorialTheme {
//        val context2 = LocalContext.current
////        DubingPage(context2)
//    }
//}