package com.scut.fundialect.activity.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scut.fundialect.R
import com.scut.fundialect.ui.theme.BackgroundLightGrey

@Composable
fun SearchTopAppBar(searchStrIncome:String,
    onReturn:()->Unit,
    onSearch:(String)->Unit,
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(77.dp)
            .padding(0.dp),
        elevation = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        /**
         * 返回上一个页面
         * */
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
                painter = painterResource(id = R.drawable.back),
                contentDescription = "返回按钮",
//                        modifier =Modifier,
                Modifier
                    .clickable {
                        onReturn()
//                        returnActivity(navController)
                    }
                    .size(20.dp)

            )
            var text by remember { mutableStateOf(searchStrIncome) }

            Surface(
                modifier = Modifier
                    .width(300.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(30.dp),
                color = BackgroundLightGrey
            ) {
                BasicTextField(
                    maxLines = 1,
                    value = text,
                    singleLine = true,
                    onValueChange = { it ->
                        text = it
                    },
                    textStyle = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp, 6.dp, 0.dp, 0.dp).align(Alignment.CenterVertically)
                )
            }
            Text(
                text = "搜索",
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable {
                        onSearch(text)
                    },
            )

        }

    }
}
