package com.scut.fundialect.activity.learn

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.scut.fundialect.activity.publicCompose.MyButtonAppBar
import com.scut.fundialect.database.helper.LearnVideoHelper.getCollectedVideo

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

        val  collectedVideos =  getCollectedVideo()
        Column() {
            
        }
    }
}


