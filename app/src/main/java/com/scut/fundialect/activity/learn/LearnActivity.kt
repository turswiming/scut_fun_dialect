package com.scut.fundialect.activity.learn

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.ui.theme.ComposeTutorialTheme


class LearnActivity : BaseComposeActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this,"准备载入compose", Toast.LENGTH_SHORT).show()
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.Black) {
//                    LearnMainPage(this)
                }
            }
        }
    }

}
fun goToSearchPage(navController: NavHostController) {
    navController.navigate("SearchPage/0")

}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
public fun LearnVideoPageWithEvent(context: Context, navController: NavHostController,
) {

    var state1  by remember { mutableStateOf(0) }
    var showedPage1 = remember {
        mutableStateListOf(2,3,4,5)
    }
    val titles = listOf("精选", "词库")
    Scaffold(
        bottomBar ={
            MyButtonAppBar(
                colorMode = ColorMode.dark,
                gotoAnotherActivity = { gotoAnotherActivity(navController,it) },
                onStateChange = {

                },
                initPageIndex = 0
            )
        },
        modifier = Modifier.background(Color.Black),
        topBar = {
            /**
             * 最上面的bar，包括精选和词库
             * **/
            MyTopAppBar(goToSearchPage = { goToSearchPage(navController)},state1, titles,onStateChange = {navController.navigate("LearnPage") })

        }
    ) {
        // Screen content
        /**
         *
         *
         * 这里是“精选”页面的全部内容，包括一个视频播放器，
         *
         *
         * **/
        MySelectedPage(
            gotoDubPage={ TODO()},
            showedPage1,
            onStateChange = {
                    k, value ->
                if(k!=0){
                    showedPage1[k-1] = value+2
                    //Toast.makeText(context,"key\t$k\nvalue\t$value",Toast.LENGTH_SHORT).show()
                }
            }
        )

    }
}


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
public fun LearnColloectPageWithEvent(context: Context, navController: NavHostController,
) {

    var state1  by remember { mutableStateOf(0) }
    var showedPage1 = remember {
        mutableStateListOf(2,3,4,5)
    }
    val titles = listOf("精选", "词库")
    Scaffold(
        topBar = {
            /**
             * 最上面的bar，包括精选和词库
             * **/
            MyTopAppBar2(goToSearchPage = { goToSearchPage(navController)},1, titles,onStateChange = {navController.navigate("LearnVideoPage")})

        },
        backgroundColor = Color.Transparent,
    ) {
        // Screen content
        Row {
            //Text(text = "${state1.toString()}")
                /**
                 *
                 *
                 *
                 * 这里是“词库”页面的全部内容
                 *
                 *
                 *
                 * **/
                MyWordLibraryPageWithEvent(navController = navController)


        }

    }
}

@Composable
private fun MyTopAppBar(goToSearchPage:()->Unit,state1: Int, titles: List<String>, onStateChange: (Int) -> Unit) {
    var state11 = state1
    //界面最上面的选择框框，包括精选和词库
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier
            .background(Color.Black)
            .height(77.dp).padding(0.dp),
        contentColor = Color.Transparent,
        contentPadding =  PaddingValues(
            start = 0.dp,
            end = 0.dp)
//        Modifier.background(Color.Black),
//        modifier = Modifier
//            .background(Color.Black),
    ) { /** Top app bar content */
        Row(
            modifier = Modifier
                .background(Color.Black)
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",

                )


            // Screen content
            TabRow(
                selectedTabIndex = state11,
                Modifier
                    .width(200.dp)
                    .background(Color.Black)
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title, fontSize = 20.sp) },
                        modifier = Modifier
                            .background(Color.Black),
                        selected = state11 == index,
                        onClick = {
                            state11 = index
                            onStateChange(index)
                        }
                    )
                }
            }

            /**
             *
             * 搜索按钮
             *
             *
             * */
            Image(
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .clickable {
                        goToSearchPage()
                    },
                painter = painterResource(id = R.drawable.searchwhite),
                contentDescription = "search icon"

            )
        }

    }
}

@Preview
@Composable
fun pre(){
    MyTopAppBar2(goToSearchPage = {},1, listOf("精选","词库"),onStateChange = {})
}
@Composable
private fun MyTopAppBar2(goToSearchPage:()->Unit,state1: Int, titles: List<String>, onStateChange: (Int) -> Unit) {
    var state11 = state1
    //界面最上面的选择框框，包括精选和词库
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier
            .background(Color.Transparent)
            .height(77.dp).padding(0.dp),
        contentColor = Color.Transparent,
        contentPadding =  PaddingValues(
            start = 0.dp,
            end = 0.dp)
//        modifier = Modifier
//            .background(Color.Black),
    ) { /** Top app bar content */
        Box() {
            Image(
                painter = painterResource(id = R.drawable.learnbar),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp),
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",

                    )


                // Screen content
                TabRow(
                    backgroundColor = Color.Transparent,
                    selectedTabIndex = state11,
                    modifier = Modifier.width(200.dp)
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title,color = Color.White,fontSize = 20.sp) },
                            selected = state11 == index,
                            onClick = {
                                state11 = index
                                onStateChange(index)
                            },
//                            selectedContentColor = Color.White,
//                            unselectedContentColor = Color.Gray
                        )
                    }
                }

                /**
                 *
                 * 搜索按钮
                 *
                 *
                 * */
                Image(
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .clickable {
                            goToSearchPage()
                        },
                    painter = painterResource(id = R.drawable.searchwhite),
                    contentDescription = "search icon"

                )

            }
        }

//        }


    }
}




