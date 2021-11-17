package com.scut.fundialect.activity.learn

import android.content.Context
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.scut.fundialect.activity.compose.MyButtonAppBar

/**
 *
 *
 *
 * 这里是“词库”页面的全部内容，不包括下面的bar
 *
 *
 *
 * **/
@Composable
fun MyWordLibraryPage(context: Context) {
    Scaffold(
        bottomBar =  {
            MyButtonAppBar(onStateChange = {}, context = context,0)

        }
    ){

    }
}
