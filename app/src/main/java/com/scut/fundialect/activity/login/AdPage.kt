package com.scut.fundialect.activity.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.scut.fundialect.R
import com.scut.fundialect.activity.login.AdData.AdPages
import com.scut.fundialect.database.helper.ModelVideoHelper
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.CustomBlue
import com.scut.fundialect.ui.theme.CustomOrange
import com.scut.fundialect.ui.theme.SecondNavColor
import kotlinx.coroutines.delay

@ExperimentalPagerApi
@Composable
@Preview
fun AdWithEvent(){
    AdPage(
        onSkipAd = {
    }
    )
}

@ExperimentalPagerApi
@Composable
fun AdPage(
    onSkipAd:()->Unit
){
    val pagerState = rememberPagerState(
        //总页数
        pageCount = AdPages.size,
        //预加载的个数
        initialOffscreenLimit = 1,
        //是否无限循环
        infiniteLoop = true,
        //初始页面
        initialPage = 0
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
        ) { page ->
            //自动滚动
            LaunchedEffect(pagerState.currentPage) {
                if (pagerState.pageCount > 0) {
                    delay(2000)
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
            Image(
                painter = painterResource(id = AdPages[pagerState.currentPage].pic),
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.None,
                contentDescription = null
            )





        }
        Text(text = AdPages[pagerState.currentPage].name,fontSize = 30.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = AdPages[pagerState.currentPage].introduce,fontSize = 16.sp,textAlign = TextAlign.Center,color = Color.Gray)
        Spacer(modifier = Modifier.height(60.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Surface(
                color = switch(
                    CustomBlue,
                    SecondNavColor,
                    pagerState.currentPage == 0
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(
                        switch(
                            80.dp,
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
                    CustomBlue,
                    SecondNavColor,
                    pagerState.currentPage == 1
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(
                        switch(
                            80.dp,
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
                    CustomBlue,
                    SecondNavColor,
                    pagerState.currentPage == 2
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(
                        switch(
                            80.dp,
                            10.dp,
                            pagerState.currentPage == 2
                        )
                    )
                    .height(10.dp),
                elevation = 0.dp,
                border = null,
            ) {}


        }
    }
    Box(contentAlignment = Alignment.TopEnd,modifier = Modifier.fillMaxSize()) {
        Text(
            text = "跳过",
            modifier = Modifier
                .clickable {
                    onSkipAd()
                }
        )
    }

}
object AdData {
 class OneAdPage(val name:String,val introduce:String,val pic:Int)
    val AdPages = listOf(
        OneAdPage(
            "学习模块",
            "在这个模块中，你可以通过刷短视\n" +
                "频的方式达到趣味\n" +
                "学习的目的", R.drawable.k
        ),
        OneAdPage(
            "文化模块",
            "在这个模块中，你可以参与自己喜\n" +
                    "欢的话题，留下个人的评\n" +
                    "论，与他人云讨论", R.drawable.b
        ),
        OneAdPage(
            "学习模块",
            "在这个模块中，你可以参与热门配\n" +
                    "音活动，发布个人配音\n" +
                    "作品，与他人互动", R.drawable.j
        ),

    )
}