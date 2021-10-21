package com.scut.fundialect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.os.Message
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.scut.fundialect.helpClass.MyDataBaseHelper
import com.scut.fundialect.helpClass.SampleData
import com.scut.fundialect.ui.theme.ComposeTutorialTheme


data class Message(val author: String,val body:String)




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContent {
            ComposeTutorialTheme() {
                Conversation(SampleData.conversationSample)
            }
            val dbHelper = MyDataBaseHelper(this,"main.db",1)
            dbHelper.writableDatabase
            //Greeting(name = "lzq")



        }
    }
}




@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme() {
        Conversation(SampleData.conversationSample)
    }
}

//@Preview
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
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))
        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant ,
                style = MaterialTheme.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }

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
@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn() {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}

