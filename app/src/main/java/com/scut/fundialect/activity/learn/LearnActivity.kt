package com.scut.fundialect.activity.learn

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.unit.dp
import com.google.common.base.Functions.compose
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.ui.theme.ComposeTutorialTheme
import com.scut.fundialect.ui.theme.FontBlack


class LearnActivity : BaseComposeActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(context,"准备载入compose", Toast.LENGTH_SHORT).show()
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.Black) {
                    MainPage(this)
                }
            }
        }
    }

}
fun goToSearchPage(context: Context) {
    val intent = Intent(context, LearnSearchActivity::class.java)
    context.startActivity(intent)
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
private fun MainPage(context: Context) {

    var state1  by remember { mutableStateOf(0) }
    var showedPage1 = remember {
        mutableStateListOf(2,3,4,5)
    }
    val titles = listOf("精选", "词库")
    Scaffold(
        Modifier.background(Color.Black),
        topBar = {
            /**
             * 最上面的bar，包括精选和词库
             * **/
            MyTopAppBar(context,state1, titles,onStateChange = {state1 = it })

        }
    ) {
        // Screen content
        Row {
            //Text(text = "${state1.toString()}")
            if(state1==0){
                /**
                 *
                 *
                 * 这里是“精选”页面的全部内容，包括一个视频播放器，
                 *
                 *
                 *
                 *
                 * **/
                MySelectedPage(showedPage1,
                    onStateChange = {
                            k, value ->
                    if(k!=0){
                        showedPage1[k-1] = value+2
                        //Toast.makeText(context,"key\t$k\nvalue\t$value",Toast.LENGTH_SHORT).show()
                    }
                    })
            }else{
                /**
                 *
                 *
                 *
                 * 这里是“词库”页面的全部内容
                 *
                 *
                 *
                 * **/
                MyWordLibraryPage(context)
            }

        }

    }
}

@Composable
private fun MyTopAppBar(context: Context,state1: Int, titles: List<String>, onStateChange: (Int) -> Unit) {
    var state11 = state1
    //界面最上面的选择框框，包括精选和词库
    TopAppBar(
        Modifier.background(Color.Black),
//        modifier = Modifier
//            .background(Color.Black),
    ) { /** Top app bar content */
        Row(
            modifier = Modifier
                .background(Color.Black)
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                modifier = Modifier
                    .background(Color.Black),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "logo"
            )


            // Screen content
            TabRow(
                selectedTabIndex = state11,
                Modifier.width(200.dp).background(Color.Black)
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        modifier = Modifier
                            .background(Color.Black),
                        selected = state11 == index,
                        onClick = {
                            state11 = index
                            onStateChange(index)
                        }
                    )
                }
            }

            /**
             *
             * 搜索按钮
             *
             *
             * */
            Button(
                modifier = Modifier
                    .background(Color.Black),
                onClick = {
                    goToSearchPage(context)
                },
                contentPadding = PaddingValues(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 0.dp
                )
            ) {
                //Text(text = "${state11.toString()}")

                Image(
                    modifier = Modifier
                        .background(Color.Black)
                        .height(20.dp)
                        .width(20.dp),
                    painter = painterResource(id = R.drawable.searchwhite),
                    contentDescription = "search icon"

                )
            }
        }

    }
}



