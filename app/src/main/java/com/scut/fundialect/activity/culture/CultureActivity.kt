package com.scut.fundialect.activity.culture


import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.R
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.ui.theme.Transparent

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun CulturePageMainAll(
    navController: NavHostController,
    context: Context
) {
    CulturePageWithEvent(
        navController,
        context,
        CulturePageContent = {
            DubMainPage(navController)
        },
        barContent = {
            /**
             * 左上角的俩大字
             *
             * */
            Text(text = "文化", fontSize = 24.sp, color = Color.White)
            /**
             *
             * 搜索按钮
             *
             *
             * */
            Image(
                painter = painterResource(id = R.drawable.searchwhite),
                contentDescription = "search icon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        navController.navigate("SearchPage/${2}")

                    }

            )
        }
    )
}
@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun CulturePageWithEvent(
    navController: NavHostController,
    context: Context,
    CulturePageContent:@Composable () -> Unit,
    barContent:@Composable () -> Unit

) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.culturebackground),
            contentDescription = null
        )
        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {
                /**
                 *
                 * 这里面是一整个上面的bar，包括搜索按钮和选择城市的按钮
                 *
                 * */
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            barContent()

                        }
                    }
                }
            },
            bottomBar ={
                MyButtonAppBar(
                    colorMode = ColorMode.light,
                    gotoAnotherActivity = {gotoAnotherActivity(navController,it)},
                    onStateChange = {

                    },
                    initPageIndex = 2
                )
            }

        ) {
            CulturePageContent()
        }
    }

}


@ExperimentalPagerApi
@Composable
fun DubMainPage(navController:NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

    }
}