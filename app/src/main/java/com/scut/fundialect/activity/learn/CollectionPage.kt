package com.scut.fundialect.activity.learn

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.scut.fundialect.R
import com.scut.fundialect.activity.learn.ui.theme.white
import com.scut.fundialect.activity.publicCompose.MyButtonAppBar
import com.scut.fundialect.database.CityDataBaseHelper
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.LearnVideoHelper.getCollectedVideo
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.black

/**
 *
 *
 *
 * 这里是“词库”页面的全部内容，不包括下面的bar
 *
 *
 *
 * **/
@ExperimentalFoundationApi
@Composable
fun MyWordLibraryPage(context: Context) {
    Scaffold(
        bottomBar =  {
            MyButtonAppBar(onStateChange = {}, context = context,0)

        }
    ){

        Column() {
            var isShow  by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                Alignment.CenterEnd
            ){
                Box(
                    Modifier
                        .size(40.dp)
                        .clickable {
                            isShow = !isShow
                        }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "右上角是否弹出的按钮",
                        Modifier
                            .rotate(
                                switch(
                                    0.0,
                                    180.0,
                                    isShow
                                ).toFloat()
                            )
                    )
                }

            }
            Box(Modifier.fillMaxSize()) {
                /**
                 * 这个里面是能够弹出的下拉选择框。
                 *
                 * */
                Surface(
                    modifier = Modifier
                        .height(switch(200.dp, 1.dp, isShow))
                        .fillMaxWidth(),
                    shape = RoundedCornerShape (0. dp,0.dp,25.dp,25.dp),
                    color = black
                ) {
                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(minSize = 64.dp)
                    ) {

                        val cityList = CityHelper.getChildCity(1)
                        cityList.add(0, CityDataBaseHelper.CityData(0,"全国"))
                        items(cityList.size) { index ->
                            /**
                             *
                             *
                             *
                             * 这个下面的东西代表着每一个单独的写有城市名字的小按钮
                             *
                             *
                             *
                             * */
                            Box(
                                modifier = Modifier
                                    .clickable {

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
                /**
                 *
                 * 这个里面是一整个滚动列表
                 * */
                ScrollBoxes()
            }


        }
    }
}
@Composable
fun ScrollBoxes() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()).fillMaxSize()
    ) {
        val  collectedVideos =  getCollectedVideo()

        collectedVideos.forEach { _videoData ->
            /**
             *
             * 这里面是每一个小的视频卡片
             *
             * */
        }
    }
}


