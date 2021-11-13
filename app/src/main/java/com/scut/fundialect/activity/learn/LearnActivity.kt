package com.scut.fundialect.activity.learn

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import com.scut.fundialect.activity.learn.ui.theme.FunDialectTheme

class LearnActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    handpage(0,listOf(1,1,1,2),0)
                }
            }
        }
    }
}

@Composable
fun Greeting3(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    FunDialectTheme {
        Greeting3("Android")
    }
}

@Composable
private fun handpage(showedPage:Int, showedCityId:List<Int>,state: Int) {

    var state1  by remember { mutableStateOf(state) }

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
                selectedPage(showedCityId)
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
                Text(text = "${state11.toString()}")

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "search icon"
                )
            }
        }

    }
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