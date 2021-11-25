package com.scut.fundialect.activity.publicCompose

import android.content.Context
import android.content.Intent
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
fun MyButtonAppBar(
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
                        gotoAnotherActivity(context,index)
                    }
                )
            }
        }
    }
}
fun gotoAnotherActivity(context: Context,index:Int) {
    val intent:Intent = when(index){
        0-> Intent(context, LearnActivity::class.java)
        1-> Intent(context, CultureActivity::class.java)
        2-> Intent(context, DubingActivity::class.java)
        else-> Intent(context, MyselfActivity::class.java)

    }
    context.startActivity(intent)
}