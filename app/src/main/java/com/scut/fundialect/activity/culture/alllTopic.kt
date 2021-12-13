package com.scut.fundialect.activity.culture

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.MyApplication
import com.scut.fundialect.R
import com.scut.fundialect.activity.dubing.DubingPageWithEvent
import com.scut.fundialect.activity.dubing.DubingPageWithEvent2
import com.scut.fundialect.database.helper.TopicHelper
import com.scut.fundialect.database.helper.UserHelpr
import com.scut.fundialect.ui.theme.FontGray

@ExperimentalPagerApi
@Composable
fun AlllTopicPageWithEvent() {

}
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun AlllTopicPage(navController: NavHostController){


    DubingPageWithEvent2(
        initPageIndex = 1,
        R.drawable.detal,
        navController = navController,
        context = MyApplication.context,
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
            Text(text = "全部话题",color = Color.White,fontSize = 20.sp)
            /**
             *
             * 搜索按钮
             *
             *
             * */
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
                        navController.navigate("SearchPage/${1}")

                    }

            )
        },
        dubPageContent={
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()).padding(15.dp)
            ) {
                val info =  TopicHelper.getCollectedTopic()
                info.forEach {
                    CultureShowBoxWithEvent(it,clickable = {navController.navigate("TopicDetalPage/${it.videoId}")})
                    Spacer(modifier = Modifier.size(20.dp))
                }
            }


        }
    )
}
