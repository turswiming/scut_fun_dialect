package com.scut.fundialect.activity.learn

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.shapes.Shape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.help.switch
import com.scut.fundialect.ui.theme.*

class LearnSearchActivity : BaseComposeActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SearchPage(this)
                }
            }
        }
    }
    @ExperimentalFoundationApi
    @Composable
    private fun SearchPage(context: Context) {
        Scaffold(
            topBar = {
                TopAppBar(modifier = Modifier.fillMaxWidth()) {
                    /**
                     * 返回上一个页面
                     * */
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "返回按钮",

                            Modifier.clickable {
                                returnActivity()
                            }

                        )
                        var text by remember { mutableStateOf("") }

                        Surface(
                            modifier = Modifier
                                .width(300.dp)
                                .height(60.dp),
                            shape = RoundedCornerShape(15.dp),
                        ) {
                            BasicTextField(
                                value = text,
                                singleLine = true,
                                onValueChange = {it->
                                    text=it
                                },
                                textStyle = TextStyle(fontSize = 30.sp),
                            modifier = Modifier.fillMaxSize().padding(5.dp)
                            )
                        }
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "搜索按钮",
                            Modifier.clickable {
                                TODO("根据输入框内容执行搜索")
                            }

                        )

                    }

                }
            }
        ){
/**
 * 搜索页面的内容
 * 
 * */       
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize().padding(20.dp)
                    ,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    /**
                     *
                     *
                     * 搜索页的文字们
                     *
                     *
                     * */
                    
                    Text(text = "历史记录")

                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(minSize = 80.dp)
                    ) {

                        val textList = listOf<String>(
                            "橘色",
                            "曹建你生",
                            "东方不败",
                            "西方真经",
                            "南方火锅",
                            "东北烧烤",
                            "蓝色",
                            "绿",
                        )
                        items(textList.size) { index ->
                            /**
                             *
                             *
                             *
                             * 这个下面的东西代表着每一个单独的写有历史搜索内容的小按钮*
                             *
                             *
                             *
                             * */
                            Box(
                                modifier = Modifier
                                    .clickable {
                                        //Toast.makeText(context,"key\t$cityStateLast\nvalue\t$index",Toast.LENGTH_SHORT).show()
                                    }
                                    .width(70.dp)
                                    .height(32.dp)
                                    .padding(5.dp)
                                    .background(
                                        color = FirstNavColor,
                                        shape = RoundedCornerShape(5.dp),
                                    ),
                                contentAlignment = Alignment.Center




                                ) {
                                Text(
                                    text = AnnotatedString(textList[index]),
                                    color = FontWhite,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),





                                )
                            }


                        }
                    }
                    Text(text = "热门搜索")
                    val hotSearch = listOf(
                        "各种方言的你好",
                        "四川话的耙耳朵是啥意思",
                        "抵押热巴出席活动时着急彪方言",
                        "各种方言的你好",
                        "四川话的耙耳朵是啥意思",
                        "抵押热巴出席活动时着急彪方言"
                    )
                    Column() {
                        var index = 0
                        hotSearch.forEach(){ it ->
                            index++
                            Row(
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = index.toString(),
                                    color = switch(
                                        CustomOrange,
                                        FontBlack,
                                        index<=3
                                    ),
                                    fontSize = 30.sp
                                )
                                Text(
                                    text = it
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    
    private fun returnActivity() {
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }
}