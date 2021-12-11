package com.scut.fundialect.activity.publicCompose

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.culture.CultureActivity
import com.scut.fundialect.activity.dubing.DubingActivity
import com.scut.fundialect.activity.learn.LearnActivity
import com.scut.fundialect.activity.myself.MyselfActivity

class ButtonAppBar {
    class ButtomTitles(name:String, image: Int){
        private var _name:String = name
        private var _image:Int = image
        fun getTheName():String{
            return _name
        }
        fun getTheImage():Int{
            return _image
        }
    }


}
@Preview
@Composable
fun MyPreview(){

}
@Composable
fun MyButtonAppBar(navController: NavHostController,
    onStateChange: (Int) -> Unit,
    context: Context,
    initPageIndex: Int
) {
    BottomAppBar {
        var buttomStateNow =initPageIndex
        TabRow(selectedTabIndex = buttomStateNow) {
            val titles = listOf(
                ButtonAppBar.ButtomTitles("学习", R.drawable.ic_launcher_foreground),
                ButtonAppBar.ButtomTitles("文化", R.drawable.ic_launcher_foreground),
                ButtonAppBar.ButtomTitles("配音", R.drawable.ic_launcher_foreground),
                ButtonAppBar.ButtomTitles("我的", R.drawable.ic_launcher_foreground),
            )
            titles.forEachIndexed { index, title, ->
                Tab(
                    text = {
                        /**
                         * 每个下面下巴导航栏的文字
                         *
                         * **/
                        Text(title.getTheName(),
                        fontSize=13.sp,)
                    },
                    selected = buttomStateNow == index,
                    icon = {
                        /**
                         * 每个下面下巴导航栏的图标
                         *
                         * **/
                        Image(
                            painter = painterResource(id = title.getTheImage()),
                            contentDescription = "title.getTheName()",
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                        )
                    },
                    onClick = {
                        buttomStateNow = index
                        onStateChange(index)
                        gotoAnotherActivity(navController,index)
                    }
                )
            }
        }
    }
}
fun gotoAnotherActivity(navController: NavHostController, index:Int) {
    when(index){
        0-> navController.navigate("LearnPage")
        1-> navController.navigate("CulturePage")
        2-> navController.navigate("DubbingPage")
        3-> navController.navigate("MyselfPage")
        else-> Toast.makeText(context,"outOfRange",Toast.LENGTH_SHORT).show()

    }
}