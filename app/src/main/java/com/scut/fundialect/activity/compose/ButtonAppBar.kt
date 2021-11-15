package com.scut.fundialect.activity.compose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.culture.CultureActivity
import com.scut.fundialect.activity.dubing.DubingActivity
import com.scut.fundialect.activity.learn.LearnActivity
import com.scut.fundialect.activity.myself.MyselfActivity

public class ButtonAppBar {
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
public fun MyButtonAppBar(
    onStateChange: (Int) -> Unit,
    context: Context
) {
    BottomAppBar {
        var buttomStateNow by remember { mutableStateOf(0) }
        TabRow(selectedTabIndex = buttomStateNow) {
            val titles = listOf<ButtonAppBar.ButtomTitles>(
                ButtonAppBar.ButtomTitles("学习", R.drawable.ic_launcher_foreground),
                ButtonAppBar.ButtomTitles("文化", R.drawable.ic_launcher_foreground),
                ButtonAppBar.ButtomTitles("配音", R.drawable.ic_launcher_foreground),
                ButtonAppBar.ButtomTitles("我的", R.drawable.ic_launcher_foreground),
            )
            titles.forEachIndexed { index, title, ->
                Tab(
                    text = {
                        /**
                         * 每个下面下巴导航栏的文字**/
                        Text(title.getTheName())
                    },
                    selected = buttomStateNow == index,
                    icon = {
                        /**
                         * 每个下面下巴导航栏的图标**/
                        Image(
                            painter = painterResource(id = title.getTheImage()),
                            contentDescription = "title.getTheName()"
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