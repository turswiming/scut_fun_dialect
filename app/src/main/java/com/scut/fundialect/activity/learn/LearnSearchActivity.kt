package com.scut.fundialect.activity.learn

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.learn.ui.theme.FunDialectTheme

class LearnSearchActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SearchPage(this)
                }
            }
        }
    }
    @Composable
    private fun SearchPage(context: Context) {
        Scaffold(
            topBar = {
                TopAppBar() {
                    /**
                     * 返回上一个页面
                     * */
                    Row() {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "返回按钮",
                            Modifier.clickable {
                                TODO("返回上一个页面")
                            }

                        )
                        var text by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Label") }
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "搜索按钮",
                            Modifier.clickable {
                                TODO("根据输入框内容执行搜索")
                            }

                        )

                    }

                }
            }){

        }
    }
}