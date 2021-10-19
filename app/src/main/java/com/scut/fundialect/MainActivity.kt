package com.scut.fundialect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.Message
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scut.fundialect.ui.theme.ComposeTutorialTheme


data class Message(val author: String,val body:String)




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent {
            ComposeTutorialTheme {
                MessageCard(Message("Android", "Jetpack Compose"))
            }
            Greeting(name = "lzq")
        }
    }
}

@Preview
@Composable
fun PreviewGreeting() {
    //Greeting("Android")
    ComposeTutorialTheme {
        MessageCard(
            msg = Message("Colleague", "Take a look at Jetpack Compose, it's great!")
        )

    }
}
@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = msg.author ,
                color = MaterialTheme.colors.secondaryVariant
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body)
        }
    }
}


@Composable
fun Greeting(name: String) {
    Row(
        modifier = Modifier.padding(all = 9.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "图片测试",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(

        ) {
            Text(text = "Hello$name!",
                    modifier =  Modifier.
                    padding(all = 8.dp)
            )
            Text(text = "宁来啦！")
            Text(text = "$name 我等您好久啦")
        }
    }


}