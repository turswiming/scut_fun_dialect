package com.scut.fundialect.activity.dubing

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.learn.goToSearchPage
import com.scut.fundialect.activity.publicCompose.ButtonAppBar
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.ui.theme.ComposeTutorialTheme
import com.scut.fundialect.ui.theme.CustomOrange
import com.scut.fundialect.ui.theme.Transparent
import org.intellij.lang.annotations.JdkConstants

class DubingActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                /**
                 * 这个页面的背景图放在这里。
                 * */
                Surface(color = MaterialTheme.colors.background) {
                    DubingPage(this,)
                }
            }
        }
    }
}

@Composable
fun DubingPage(context: Context) {
    Scaffold(
        topBar = {
            /**
             * 
             * 这里面是一整个上面的bar，包括搜索按钮和选择城市的按钮
             * 
             * */
            DubingTopAppBar()
        },
        bottomBar ={
            ButtonAppBar()
        }

    ) {
        
    }
}
/**
 *
 * 这里面是一整个上面的bar，包括搜索按钮和选择城市的按钮
 *
 * */
@Composable
private fun DubingTopAppBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
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
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                /**
                 * 左上角的俩大字
                 *
                 * */
                Text(text = "配音")
                /**
                 *
                 * 搜索按钮
                 *
                 *
                 * */
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "search icon",
                    modifier = Modifier.clickable {
                        goToSearchPage(context)

                    }

                )
            }
            /**
             * bar下面选择城市的东西
             *
             * */
            var cityStateNow by remember { mutableStateOf(0) }
            var cityStateLast  by remember { mutableStateOf(0) }
            var showedCityId = remember {
                mutableStateListOf(2,3,4,5)
            }
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

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTutorialTheme {
        DubingPage(context)
    }
}