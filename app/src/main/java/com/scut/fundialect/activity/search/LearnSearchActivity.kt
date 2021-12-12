package com.scut.fundialect.activity.search

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.compose.SearchTopAppBar
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
//                    SearchPage(this)
                }
            }
        }
    }

}
@ExperimentalFoundationApi
@Composable
fun SearchPageWithEvent(context: Context, navController: NavHostController,location:Int) {
    Scaffold(
        topBar = {
            SearchTopAppBar("",
                onReturn = {
                    navController.popBackStack()
                },
                onSearch = {
                    if(it!=""){
                        navController.navigate("SearchOutcomePage/${it}/${location}")

                    }
                }
            )
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
                    .fillMaxSize()
                    .padding(20.dp),
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

                Spacer(modifier = Modifier
                    .height(2.dp)
//                    .background(Color.White)
                )
                Text(
                    text = "历史记录",
                    fontSize=20.sp,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))

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
                                    color = BackgroundLightGrey,
                                    shape = RoundedCornerShape(5.dp),
                                ),
                            contentAlignment = Alignment.Center




                        ) {
                            Text(
                                text = AnnotatedString(textList[index]),
                                fontSize=14.sp,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }


                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "热门搜索",
                    fontSize=20.sp,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))

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
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .padding(0.dp,0.dp,0.dp,10.dp)
                        ) {
                            Text(
                                text = index.toString(),
                                color = switch(
                                    CustomOrange,
                                    FontBlack,
                                    index<=3
                                ),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier
                                .width(5.dp)
                                .background(Color.White))
                            Text(
                                text = it,
                                fontSize=16.sp,

                                )
                        }
                    }
                }
            }
        }
    }
}



private fun returnActivity(navController: NavHostController) {
    navController.popBackStack()
}