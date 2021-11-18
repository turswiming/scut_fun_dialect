package com.scut.fundialect.activity.learn

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.scut.fundialect.R
import com.scut.fundialect.activity.learn.ui.theme.Purple200
import com.scut.fundialect.activity.learn.ui.theme.white
import com.scut.fundialect.activity.publicCompose.MyButtonAppBar
import com.scut.fundialect.database.CityDataBaseHelper
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.CityHelper.getCityName
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
                                    .clickable { isShow = false }
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

            }


        }
    }
}

@Composable
fun ScrollBoxes() {
    Column(
        modifier = Modifier
            .background(Purple200)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    ) {
        val  collectedVideos =  getCollectedVideo()
        collectedVideos.forEach { _videoData ->
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
            ) {
                Surface(
                    modifier = Modifier
                        .background(
                            shape = CutCornerShape(10.dp),
                            color = black
                        )
                        .fillMaxSize()
                ) {
                    
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
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
                                    .height(60.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                _videoData.videoName.forEach { char ->
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
                                        Text(
                                            text = pinyin,
                                            color = black,
                                            modifier = Modifier
                                                .width(50.dp),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(text = char.toString())
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
                                    painter = painterResource(id = R.drawable.ic_launcher_background),
                                    contentDescription = "只听声音",
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_background),
                                    contentDescription = "只听声音",
                                    modifier = Modifier.size(30.dp)

                                )

                            }
                        }
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween) {
                                /**
                                 * 左下角的释义和视频介绍
                                 *
                                 * */
                                Text(text = "释义")
                                Text(text = _videoData.videoIntroduce)
                            }
                            /**
                             * 右下角的城市归属和图片
                             *
                             * */
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_launcher_background), 
                                    contentDescription = "城市背景图"
                                )
                                Text(text = getCityName(_videoData.videoBelongCityId))
                            }
                        }
                    }
                    
                    
                    

                }
            }
        }
    }
}


