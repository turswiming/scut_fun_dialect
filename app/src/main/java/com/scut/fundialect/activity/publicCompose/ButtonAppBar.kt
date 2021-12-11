package com.scut.fundialect.activity.publicCompose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.AppBarDefaults.ContentPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.enum.ColorMode
import com.scut.fundialect.help.switch

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
    MyButtonAppBar(ColorMode.dark,gotoAnotherActivity = {},onStateChange = {},1)
}
@Composable
fun MyButtonAppBar(
    colorMode: ColorMode,
    gotoAnotherActivity:(Int)->Unit,
    onStateChange: (Int) -> Unit,
    initPageIndex: Int
) {
    BottomAppBar(
        backgroundColor = switch(Color.White, Color.Black,colorMode==ColorMode.light),
        elevation = 0.dp,
        contentPadding = PaddingValues(
            start = 0.dp,
            end = 0.dp
        )
    ) {
        var buttomStateNow =initPageIndex
        TabRow(
            selectedTabIndex = buttomStateNow,
            backgroundColor = Color.Transparent
        ) {

                val titles = listOf(
                    ButtonAppBar.ButtomTitles("学习", switch(R.drawable.learnlight,R.drawable.learndark,initPageIndex==0)),
                    ButtonAppBar.ButtomTitles("文化", switch(R.drawable.culturelight,R.drawable.culturedark,initPageIndex==1)),
                    ButtonAppBar.ButtomTitles("配音", switch(R.drawable.dublight,R.drawable.dubdark,initPageIndex==2)),
                    ButtonAppBar.ButtomTitles("我的", switch(R.drawable.myselflight,R.drawable.myselfdark,initPageIndex==3)),
                )



            titles.forEachIndexed { index, title, ->
                Tab(
                    text = {
                        /**
                         * 每个下面下巴导航栏的文字
                         *
                         * **/
                        Text(title.getTheName(),
                        fontSize=13.sp,
                            color = switch(Color.Black, Color.White,colorMode==ColorMode.light)
                        )
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
                        gotoAnotherActivity(index)
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