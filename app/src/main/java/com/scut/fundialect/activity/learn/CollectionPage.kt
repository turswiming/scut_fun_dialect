package com.scut.fundialect.activity.learn

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.scut.fundialect.R

import com.scut.fundialect.activity.publicCompose.MyButtonAppBar
import com.scut.fundialect.database.CityDataBaseHelper
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.CityHelper.getCityName
import com.scut.fundialect.database.helper.LearnVideoHelper.getCollectedVideo
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.BackgroundLightGrey
import com.scut.fundialect.ui.theme.CustomOrange

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
                    .width(80.dp)
                    .height(40.dp)
                    .background(BackgroundLightGrey),
                Alignment.CenterEnd
            ){
                Box(
                    Modifier
                        .size(40.dp)
                        .background(BackgroundLightGrey)
                        .clickable {
                            isShow = !isShow
                        }) {
                    Image(
                        painter = painterResource(id = R.drawable.down),
                        contentDescription = "右上角是否弹出的按钮",
                        Modifier
                            .background(BackgroundLightGrey)
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
            Box(Modifier.fillMaxSize().background(BackgroundLightGrey)) {
                /**
                 *
                 * 这个里面是一整个滚动列表
                 * */
                ScrollBoxes()
                /**
                 * 这个里面是能够弹出的下拉选择框。
                 *
                 * */
                Surface(
                    modifier = Modifier
                        .height(switch(200.dp, 1.dp, isShow))
                        .fillMaxWidth()
                        .background(BackgroundLightGrey),
                    shape = RoundedCornerShape (0. dp,0.dp,25.dp,25.dp),
                    color = BackgroundLightGrey,
                    elevation=10.dp,
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
                                    .clickable { isShow = false }
                                    .width(64.dp)
                                    .height(32.dp)
                                    .background(BackgroundLightGrey),


                                ) {
                                Text(
                                    text = AnnotatedString(cityList[index].getTheName()),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth().background(BackgroundLightGrey)

                                )
                            }


                        }
                    }


                }

            }


        }
    }
}

@Composable
fun ScrollBoxes() {
    val  collectedVideos =  getCollectedVideo()
    LazyColumn(
        modifier = Modifier
            .background(BackgroundLightGrey)

//            .verticalScroll(rememberScrollState())
//            .fillMaxWidth()
    ) {
        items(collectedVideos.size){index ->
            /**
             *
             * 这里面是每一个小的视频卡片
             *
             * */
            Box(
                modifier = Modifier
                    .padding(10.dp, 20.dp, 10.dp, 20.dp)
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(
                        shape = RoundedCornerShape (25.dp),
                        color = Color.White)


            ) {
                Surface(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape (25.dp),
                            color = Color.White
                        )
                        .fillMaxSize()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                shape = RoundedCornerShape (50.dp),
                                color = Color.White),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(Color.White),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            /**
                             *
                             * 这里面包括了标题文字及其拼音
                             * */
                            Row(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(80.dp)
                                    .background(Color.White),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                collectedVideos[index].videoName.forEach { char ->
                                    /**
                                     *
                                     * 这里面包括了标题文字及其拼音的
                                     *
                                     * 每一个字
                                     *
                                     * */
                                    val pinyin = PinyinHelper.convertToPinyinString(
                                        char.toString(),
                                        ",",
                                        PinyinFormat.WITH_TONE_MARK
                                    )
                                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                                        Spacer(modifier = Modifier.height(2.dp).background(Color.White))
                                        Text(
                                            text = pinyin,
                                            color = Color.Black,
                                            fontSize=14.sp,
                                            modifier = Modifier
                                                .width(50.dp)
                                                .background(Color.White),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(2.dp).background(Color.White))
                                        Text(
                                            text = char.toString(),
                                            color = Color.Black,
                                            fontSize=30.sp,
                                            fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                            /**
                             * 这里面包括了右边两个按钮
                             *
                             * */
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.sound_only),
                                    contentDescription = "只听声音",
                                    modifier = Modifier.size(30.dp).background(Color.White)
                                )
                                Spacer(modifier = Modifier.width(5.dp).background(Color.White))
                                Image(
                                    painter = painterResource(id = R.drawable.mv_play),
                                    contentDescription = "播放音频",
                                    modifier = Modifier.size(30.dp).background(Color.White)
                                        .clickable {
                                        TODO()
                                    }

                                )

                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.White),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceBetween) {
                                /**
                                 * 左下角的释义和视频介绍
                                 *
                                 * */
                                Spacer(modifier = Modifier.width(15.dp).background(Color.White))
                                Text(
                                    text = "释义",
                                    color = Color.Black,
                                    fontSize=14.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                                )
                                Text(
                                    text = collectedVideos[index].videoIntroduce,
                                    color = Color.Black,
                                    fontSize=14.sp,
                                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp))
                            }
                            /**
                             * 右下角的城市归属和图片
                             *
                             * */
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.city_card),
                                    contentDescription = "城市背景图"
                                )
                                Text(text = getCityName(collectedVideos[index].videoBelongCityId))
                            }
                        }
                    }




                }
            }
        }

    }
}


