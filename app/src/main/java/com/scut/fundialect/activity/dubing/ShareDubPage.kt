package com.scut.fundialect.activity.dubing

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.activity.video.ShareButton
import kotlinx.coroutines.launch


@Composable
fun ShareDubPageWithEvent(){

}

@ExperimentalMaterialApi
@Composable
fun ShareDubPage(navHostController: NavHostController){

    val shareState = rememberModalBottomSheetState(
        ModalBottomSheetValue.HalfExpanded
    )
    val shareScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetState = shareState,
        sheetContent={
            Column(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "分享至",
                            fontSize = 20.sp,
                        )
                    }
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd) {
                        Image(
                            painter = painterResource(id = R.drawable.close_black),
                            contentDescription = "关闭分享",
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
                                .clickable {
                                    shareScope.launch { shareState.hide() }
                                })
                    }
                }
                Row(
                    Modifier.height(80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(5.dp))
                    ShareButton(
                        "微信好友",
                        R.drawable.wx_friend,
                        onClick = {
                            Toast.makeText(context, "分享给了微信好友", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                    ShareButton(
                        "微信朋友圈",
                        R.drawable.wx_public,
                        onClick = {
                            Toast.makeText(context, "分享给了微信朋友圈", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                    ShareButton(
                        "QQ好友",
                        R.drawable.qq_friend,
                        onClick = {
                            Toast.makeText(context, "分享给了QQ好友", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                    ShareButton(
                        "QQ空间",
                        R.drawable.qq_public,
                        onClick = {
                            Toast.makeText(context, "分享给了QQ空间", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                    ShareButton(
                        "今日头条",
                        R.drawable.today_news,
                        onClick = {
                            Toast.makeText(context, "分享给了今日头条", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                    ShareButton(
                        "微博",
                        R.drawable.wb_news,
                        onClick = {
                            Toast.makeText(context, "分享给了微博", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                }
                Row(
                    Modifier.height(80.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ShareButton(
                        "保存本地",
                        R.drawable.download_local,
                        onClick = {
                            Toast.makeText(context, "已保存本地", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                    ShareButton(
                        "复制链接",
                        R.drawable.copy_address,
                        onClick = {
                            Toast.makeText(context, "已复制链接", Toast.LENGTH_SHORT).show()
                            navHostController.navigate("DubbingPage")

                        }
                    )
                    //下面我复制了四个空盒子用来做占位
                    val list = listOf(1, 2, 3, 4)
                    list.forEach { _ ->
                        Box(
                            modifier = Modifier
                                .height(75.dp)
                                .width(65.dp),
                        ) {

                        }

                    }


                }

            }

        }
        ){
        Image(
            painter = painterResource(id = R.drawable.sharedubpage) ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    Toast.makeText(context,"录制完成",Toast.LENGTH_LONG).show()
                    navHostController.navigate("DubbingPage")
                }
        )
    }
}
