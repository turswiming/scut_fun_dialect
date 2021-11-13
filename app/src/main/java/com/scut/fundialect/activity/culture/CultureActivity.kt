package com.scut.fundialect.activity.culture

import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.culture.ui.theme.FunDialectTheme

class CultureActivity : BaseComposeActivity() {
    var showedPage = 0
    var showedCityId = listOf<Int>(1,2,3,4)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    var state by remember { mutableStateOf(0) }

                    handpage(0,listOf(1,1,1,2),state)

                }
            }
        }

    }
    fun switchState(index:Int) {
        showedPage = index
    }
    @Composable
    private fun handpage(showedPage:Int, showedCityId:List<Int>,state: Int) {

        
        val titles = listOf("精选", "词库")
        Scaffold(
            topBar = {
                var state1  by remember { mutableStateOf(state) }
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
                            selectedTabIndex = state1,
                            Modifier.width(200.dp)
                        ) {
                            titles.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title) },
                                    selected = state1 == index,
                                    onClick = {
                                        state1 = index
                                        switchState(index)
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
                            Text(text = "${state1.toString()}")

                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "search icon"
                            )
                        }
                    }

                }

            }
        ) {
            // Screen content
            Row() {
                Text(text = "${state.toString()}")
                if(state==0){
                    selectedPage(showedCityId)
                }
                else{

                }
            }
            
        }
    }



    @Composable
    private fun myTopAppBar(state: Int, titles: List<String>) {

    }

    @Composable
    private fun selectedPage(showedCityId: List<Int>) {
        Scaffold(
            bottomBar = {
                BottomAppBar { /* Bottom app bar content */ }
            }
        ) {
            var cityStateNow by remember { mutableStateOf(0) }
            // Screen content
            TabRow(
                selectedTabIndex = cityStateNow,

                ) {
                showedCityId.forEachIndexed { index, cityId ->
                    Tab(
                        text = { Text("TODO") },
                        selected = cityStateNow == index,
                        onClick = { cityStateNow = index }
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun preview(){
        handpage(0,listOf(1,1,1,2),0)
    }
}

