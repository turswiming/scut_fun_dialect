package com.scut.fundialect.activity.myself

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.publicCompose.MyButtonAppBar
import com.scut.fundialect.ui.theme.ComposeTutorialTheme
import com.scut.fundialect.ui.theme.Transparent

class MyselfActivity : BaseComposeActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    MyselfPage()
                }
            }
        }
    }

}

@ExperimentalPagerApi
@Composable
fun MyselfPage(navController: NavHostController) {
    val context = LocalContext.current
    Scaffold(
        bottomBar = {
            /**
         *
         * 下面的bar
         *
         * */
            MyButtonAppBar(
                navController = navController,
                onStateChange = {

                },
                context = context,
                initPageIndex = 3
            )
                    },
        topBar = {
        /**
         *
         * 上面的bar。包括頭像，定位，設置等
         *
         * */
        MyselfTopBar(onSettingIconClick={},onImageClick={})
    }
    ) {
        MyselfMainPage()
    }

}

@ExperimentalPagerApi
@Composable
fun MyselfMainPage() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        /**
         *
         * 上面的方框，包括打卡地點，粉絲，關注，詞庫等
         *
         * */
        MyBox()
        /**
         *
         * 中間的兩個方框，參與話題和我的足跡
         *
         * */
        Row(
            Modifier.height(80.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyButton2(onclick = { /*TODO*/ }, text1 = "參與話題",)
            MyButton2(onclick = { /*TODO*/ }, text1 = "我的足跡",)

        }
        val pagerState = rememberPagerState(
            //总页数
            pageCount = 3,
            //预加载的个数
            initialOffscreenLimit = 1,
            //是否无限循环
            infiniteLoop = true,
            //初始页面
            initialPage = 0
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
        ) { page ->
            //自动滚动
//            LaunchedEffect(pagerState.currentPage) {
//                if (pagerState.pageCount > 0) {
//                    delay(2000)
//                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                }
//            }

            Surface(
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxSize()
            ) {

            }
        }
    }
}

@Composable
private fun MyBox() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        /**
         *
         * 上面綠色的背景圖
         *
         * */
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "綠色的背景圖",
            modifier = Modifier.fillMaxSize()
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                /**
                 * 綠色背景上面的文字，你已打卡等
                 * */
                Column() {
                    Text(text = "你已打卡")
                    Text(text = "3個城市的方言")
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        Modifier.clickable { TODO() }
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    Modifier.clickable { TODO() }

                )
            }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(15.dp),
                color = Color.White
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    /**
                     * 粉絲，關注和詞庫這三個按鈕
                     * */
                    MyButton(onclick = {}, text1 = "粉絲", text2 = "123")
                    MyButton(onclick = {}, text1 = "關注", text2 = "13")
                    MyButton(onclick = {}, text1 = "詞庫", text2 = "100")

                }
            }
        }
    }
}

@Composable
private fun MyButton(onclick:()->Unit,text1:String,text2:String) {
    Surface(
        modifier = Modifier
            .width(80.dp)
            .height(50.dp)
            .clickable {
                onclick()
            },
        shape = RoundedCornerShape(15.dp),
        color = Color.White,
        elevation = 2.dp

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = text1)
            Text(text = text2)
        }

    }
}
@Composable
private fun MyButton2(onclick:()->Unit,text1:String) {
    Surface(
        modifier = Modifier
            .width(80.dp)
            .height(50.dp)
            .clickable {
                onclick()
            },
        shape = RoundedCornerShape(15.dp),
        color = Color.White,
        elevation = 2.dp

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
        Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text1)
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null
            )
        }

    }
}

@Composable
fun MyselfTopBar(onSettingIconClick: () -> Unit, onImageClick: () -> Unit) {
    /**
     *
     * 上面的bar。包括頭像，定位，設置等
     *
     * */
    TopAppBar(
        backgroundColor = Transparent,
        elevation = 0.dp,
        content = {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Row(){
                    /**
                     *
                     * 頭像
                     *
                     * */
                    Image(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription ="頭像" )
                    Column() {
                        /**
                         *
                         * 城市
                         *
                         * */
                        Row() {
                            Text(text = "廣州市")
                            /**
                             *
                             * 城市右邊的定位圖標
                             *
                             * */
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription =null ,
                                modifier = Modifier.size(10.dp)
                            )
                        }
                        /**
                         *
                         * 名字
                         *
                         * */
                        Text(text = "name")
                    }
                }
                Image(painter = painterResource(id =R.drawable.ic_launcher_background), contentDescription = null)
            }
        }

    )
}
