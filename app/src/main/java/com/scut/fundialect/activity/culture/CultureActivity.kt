package com.scut.fundialect.activity.culture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.culture.ui.theme.FunDialectTheme

class CultureActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    mainpage(listOf(1,1,1,2))

                }
            }
        }
    }
    @Composable
    private fun mainpage(showedID:List<Int>) {
        Scaffold(
            topBar = {
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
                            contentDescription ="logo" )


                        var state by remember { mutableStateOf(0) }
                        val titles = listOf("精选", "词库")
                        // Screen content
                        TabRow(
                            selectedTabIndex = state,
                            Modifier.width(200.dp)
                            ) {
                            titles.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title) },
                                    selected = state == index,
                                    onClick = { state = index }
                                )
                            }
                        }


                        Button(
                            onClick = { /*TODO*/ },
                            contentPadding = PaddingValues(
                                start = 0.dp,
                                top = 0.dp,
                                end = 0.dp,
                                bottom = 0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "search icon")
                        }
                    }

                }


            }
        ) {
            // Screen content
            Scaffold(
                bottomBar = {
                    BottomAppBar { /* Bottom app bar content */ }
                }
            ){
                var cityStateNow by remember { mutableStateOf(0) }
                // Screen content
                TabRow(
                    selectedTabIndex = cityStateNow,

                    ) {
                    showedID.forEachIndexed { index, cityId ->
                        Tab(
                            text = { Text("TODO") },
                            selected = cityStateNow == index,
                            onClick = { cityStateNow = index }
                        )
                    }
                }
            }
        }
    }
    @Preview
    @Composable
    fun preview(){
        mainpage(listOf(1,1,1,2))
    }
}

