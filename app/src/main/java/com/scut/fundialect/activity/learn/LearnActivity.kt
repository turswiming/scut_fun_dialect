package com.scut.fundialect.activity.learn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.stuxuhai.jpinyin.PinyinFormat
import com.github.stuxuhai.jpinyin.PinyinHelper
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.compose.MyButtonAppBar
import com.scut.fundialect.activity.compose.VideScreen
import com.scut.fundialect.activity.learn.ui.theme.FunDialectTheme
import com.scut.fundialect.activity.learn.ui.theme.Purple700
import com.scut.fundialect.activity.learn.ui.theme.white
import com.scut.fundialect.database.helper.CityHelper
import com.scut.fundialect.database.helper.CityHelper.getChildCity
import com.scut.fundialect.database.helper.LearnVideoHelper
import com.scut.fundialect.database.helper.LearnVideoHelper.getCommitNumber
import com.scut.fundialect.ui.theme.black
import com.scut.fundialect.ui.theme.blackTransparent
import kotlinx.coroutines.launch


class LearnActivity : BaseComposeActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
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
                 *
                 * 这里是“精选”页面的全部内容，包括一个视频播放器，不包括下面的bar
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
                 * 这里是“词库”页面的全部内容，不包括下面的bar
                 *
                 *
                 *
                 * **/
                MyWordLibraryPage(context)
            }

        }

    }
}
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

@Composable
private fun MyTopAppBar(context: Context,state1: Int, titles: List<String>, onStateChange: (Int) -> Unit) {
    var state11 = state1
    //界面最上面的选择框框，包括精选和词库
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary
    ) { /** Top app bar content */
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "logo"
            )


            // Screen content
            TabRow(
                selectedTabIndex = state11,
                Modifier.width(200.dp)
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
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
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "search icon"

                )
            }
        }

    }
}



