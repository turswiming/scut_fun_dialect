package com.scut.fundialect.activity.dubing

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.R
import com.scut.fundialect.activity.learn.goToSearchPage
import com.scut.fundialect.database.helper.ModelVideoHelper

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun OtherWorks(
    navController: NavHostController,
    context: Context,
){
    DubingPageWithEvent(
        navController = navController,
        context = context,
        barContent ={
            /**
             * 左上角的俩大字
             *
             * */
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "search icon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        navController.popBackStack()
                    }

            )
            Text(text = "他人作品",color = Color.White,fontSize = 20.sp)
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
        },
        dubPageContent={
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
//            Text(text = "精选",fontSize = 20.sp)
//            val modelVideos = ModelVideoHelper.getCollectedModelVideo()
//            HorizontalPicShower(modelVideos)
                Text(text = "更多精彩", fontSize = 20.sp)
                val modelVideos2 = ModelVideoHelper.getCollectedModelVideo()
                VerticalPicShower(modelVideos2)
            }
        }
    )
}