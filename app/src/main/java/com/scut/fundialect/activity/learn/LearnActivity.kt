package com.scut.fundialect.activity.learn

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.learn.ui.theme.FunDialectTheme
import com.scut.fundialect.activity.learn.ui.theme.white
import com.scut.fundialect.database.CityDataBaseHelper
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.CityHelper.getChildCity
import com.scut.fundialect.ui.theme.black

class LearnActivity : BaseComposeActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取所有省级信息内容
        val cityList = getChildCity(1)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    handpage(cityList)
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
private fun handpage(cityList: MutableList<CityDataBaseHelper.CityData>) {

    var state1  by remember { mutableStateOf(0) }
    var showedPage1 = remember {
        mutableStateListOf<Int>(2,3,4,5)
    }
    val titles = listOf("精选", "词库")
    Scaffold(
        topBar = {
            topAppBar(state1, titles,onStateChange = {state1 = it })

        }
    ) {
        // Screen content
        Row() {
            //Text(text = "${state1.toString()}")
            if(state1==0){
                selectedPage(cityList,showedPage1) { key, value ->
                    if(key!=0){
                        showedPage1[key-1] = value+2
                    }
                     }
            }
            else{

            }
        }

    }
}

@Composable
private fun topAppBar(state1: Int, titles: List<String>,onStateChange: (Int) -> Unit) {
    var state11 = state1
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary
    ) { /* Top app bar content */
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "logo"
            )


            // Screen content
            TabRow(
                selectedTabIndex = state11,
                Modifier.width(200.dp)
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = state11 == index,
                        onClick = {
                            state11 = index
                            onStateChange(index)
                        }
                    )
                }
            }


            Button(
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp
                )
            ) {
                //Text(text = "${state11.toString()}")

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "search icon"
                )
            }
        }

    }
}



@ExperimentalFoundationApi
@Composable
private fun selectedPage(
    cityList: MutableList<CityDataBaseHelper.CityData>,
    showedCityId: List<Int>,
    onStateChange: (key: Int, value: Int) -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomAppBar { /* Bottom app bar content */ }
        }
    ) {
        var cityStateNow by remember { mutableStateOf(0) }
        var cityStateLast by remember {
            mutableStateOf(1)
        }
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
                         *      那就选中第五个按钮
                         * 如果之前选中了第五个按钮
                         *      那就选中第五个按钮之前的按钮**/
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
            if(cityStateNow == 5){
                Surface(modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                    shape = RoundedCornerShape (0. dp,0.dp,10.dp,10.dp),
                    color = black
                ) {
                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(minSize = 64.dp)
                    ) {
                        items(cityList.size) { index ->
                            Box(modifier = Modifier
                                .clickable {
                                    onStateChange(cityStateLast,index)
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

            }
        }
        

    }
}





