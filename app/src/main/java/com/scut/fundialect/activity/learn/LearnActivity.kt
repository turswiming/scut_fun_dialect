package com.scut.fundialect.activity.learn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.learn.ui.theme.FunDialectTheme
import com.scut.fundialect.database.helper.CityHelper

class LearnActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    handpage()
                }
            }
        }
    }
}


@Composable
private fun handpage() {

    var state1  by remember { mutableStateOf(0) }
    var showedPage1 = remember {
        mutableStateListOf<Int>(1,2,3,4)
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
                selectedPage(showedPage1,onStateChange = {key,value -> showedPage1[key] = value })
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



@Composable
private fun selectedPage(showedCityId: List<Int>,onStateChange: (key:Int,value:Int) -> Unit) {
    Scaffold(
        bottomBar = {
            BottomAppBar { /* Bottom app bar content */ }
        }
    ) {
        var cityStateNow by remember { mutableStateOf(0) }
        // Screen content
        Column() {
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
                        onClick = { cityStateNow = index+1
                        }
                    )

                }
                Tab(selected = cityStateNow == -1,
                    onClick = { cityStateNow = -1 }){
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "加号")
                }
            }
            //LazyColumn(content = )
            if(cityStateNow == -1){
                Box(modifier = Modifier.height(400.dp)) {

                }

            }
        }
        

    }
}

@Preview
@Composable
fun preview(){
    handpage()
}