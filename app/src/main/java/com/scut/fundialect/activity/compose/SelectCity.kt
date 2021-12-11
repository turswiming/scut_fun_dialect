package com.scut.fundialect.activity.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scut.fundialect.R
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.BackgroundGray

@ExperimentalFoundationApi
@Composable
 fun SelectCity(
    colorMode: ColorMode,
    cityStateNow: Int,
    cityStateLast: Int,
    onStateChange: (key: Int, value: Int) -> Unit
) {
    var cityStateNow1 = cityStateNow

    val fontColor = switch(Color.White, Color.Black,colorMode==ColorMode.dark)
    val backgroundColor = switch(Color.Black, Color.White,colorMode==ColorMode.dark)
    Surface(
        modifier = Modifier
            .height(switch(215.dp, 1.dp, cityStateNow1 == 5))
            .fillMaxWidth()
            .background(backgroundColor),
        shape = RoundedCornerShape(0.dp, 0.dp, 25.dp, 25.dp),
        color = backgroundColor,
        elevation = 10.dp
    ) {
        Column {
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
                    Text(
                        text = AnnotatedString(cityList[index].getTheName()),
                        color = fontColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                cityStateNow1 = cityStateLast

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

@Composable
 fun pair(
    colorMode: ColorMode,
    showedCityId: List<Int>
): Pair<Int, Int> {
    var cityStateNow by remember { mutableStateOf(0) }
    var cityStateLast by remember { mutableStateOf(0) }
    val fontColor = switch(Color.White, Color.Black,colorMode==ColorMode.dark)
    val backgroundColor = switch(Color.Black, Color.White,colorMode==ColorMode.dark)

    // Screen content
    /**
     *
     * 这里是推荐页面中第二排的选择框，包括了“推荐 按钮，添加城市等按钮。
     *
     * */
    /**
     *
     * 这里是推荐页面中第二排的选择框，包括了“推荐 按钮，添加城市等按钮。
     *
     * */
    TabRow(
        selectedTabIndex = cityStateNow,
        modifier = Modifier
            .background(backgroundColor)
            .height(30.dp),

        ) {
        Tab(
            text = { Text("推荐", fontSize = 14.sp,color = fontColor) },
            modifier = Modifier
                .background(Color.Transparent)
                .padding(0.dp)
                .width(60.dp),
            selected = cityStateNow == 0,
            onClick = { cityStateNow = 0 }
        )
        showedCityId.forEachIndexed { index, cityId ->
            Tab(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(0.dp)
                    .width(40.dp),
                text = {
                    Text(
                        text = CityHelper.getCityName(cityId),
                        fontSize = 14.sp,
                        modifier = Modifier.width(80.dp),
                        color = fontColor
                    )
                },
                selected = cityStateNow == index + 1,
                onClick = {
                    cityStateLast = cityStateNow
                    cityStateNow = index + 1

                }
            )

        }
        Tab(
            modifier = Modifier
                .background(Color.Black)
                .padding(0.dp)
                .width(30.dp),
            selected = cityStateNow == 5,
            onClick = {
                /**
                 * 如果之前选中的不是第五个按钮：
                 *      -那就选中第五个按钮
                 * 如果之前选中了第五个按钮
                 *      -那就选中第五个按钮之前的按钮
                 * **/

                if (cityStateNow != 5) {
                    cityStateLast = cityStateNow
                    cityStateNow = 5
                } else {
                    cityStateNow = cityStateLast
                }
            }
        ) {
            Image(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(0.dp)
                    .height(20.dp)
                    .width(20.dp),
                painter = painterResource(
                    id = switch(R.drawable.pluswhite,R.drawable.plus_blackinwhite,colorMode==ColorMode.dark)
                ),
                contentDescription = "加号"
            )
        }
    }
    return Pair(cityStateNow, cityStateLast)
}
