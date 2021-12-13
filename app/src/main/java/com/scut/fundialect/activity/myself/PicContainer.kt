package com.scut.fundialect.activity.myself

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.scut.fundialect.MyApplication
import com.scut.fundialect.R
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.gotoAnotherActivity
import com.scut.fundialect.activity.culture.CultureShowBoxWithEvent
import com.scut.fundialect.activity.dubing.DubingPageWithEvent2
import com.scut.fundialect.database.helper.TopicHelper
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.ui.theme.Transparent

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun PicContainer(Pic:Int, navController:NavHostController){
    Image(alignment = Alignment.TopCenter,
        contentScale = ContentScale.Crop,
        painter = painterResource(id = Pic), contentDescription = null)
    DubingPageWithEvent3(
        initPageIndex = 1,
        Pic,
        navController = navController,
        context = MyApplication.context,
        barContent ={
        },
        dubPageContent={
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(15.dp)
                    .clickable { navController.popBackStack() }
            ) {

            }


        }
    )
}

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun DubingPageWithEvent3(
    initPageIndex:Int =2,
    imageId:Int = R.drawable.dubbackground,
    navController: NavHostController,
    context: Context,
    dubPageContent:@Composable () -> Unit,
    barContent:@Composable () -> Unit

) {
    Box(modifier = Modifier.fillMaxSize()) {


        Scaffold(
            backgroundColor = Color.Transparent,
            topBar = {

            },
            bottomBar ={
                MyButtonAppBar(
                    colorMode = ColorMode.light,
                    gotoAnotherActivity = { gotoAnotherActivity(navController,it) },
                    onStateChange = {

                    },
                    initPageIndex = initPageIndex
                )
            }

        ) {
            dubPageContent()

        }
    }
//    var showCitySelectPage by remember { mutableStateOf(false)}

}