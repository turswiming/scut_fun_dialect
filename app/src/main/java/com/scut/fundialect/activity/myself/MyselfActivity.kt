package com.scut.fundialect.activity.myself

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.scut.fundialect.R
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.publicCompose.MyButtonAppBar
import com.scut.fundialect.ui.theme.ComposeTutorialTheme
import com.scut.fundialect.ui.theme.Transparent

class MyselfActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyselfPage()
                }
            }
        }
    }

}

@Composable
private fun MyselfPage() {
val context = LocalContext.current
Scaffold(
    bottomBar = {
        MyButtonAppBar(onStateChange = {  }, context = context, initPageIndex = 3)
    },
    topBar = {
        MyselfTopBar(onSettingIconClick={},onImageClick={})
    }
)
{

}}

@Composable
fun MyselfTopBar(onSettingIconClick: () -> Unit, onImageClick: () -> Unit) {
    TopAppBar(
        backgroundColor = Transparent,
        elevation = 0.dp,
        content = {
            Image(painter = painterResource(R.drawable.ic_launcher_foreground), contentDescription ="頭像" )
        }

    )
}
