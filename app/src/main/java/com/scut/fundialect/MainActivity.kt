package com.scut.fundialect

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.scut.fundialect.helpClass.MyDataBaseHelper
import com.scut.fundialect.helpClass.VideoDataBaseHelper
import kotlinx.android.synthetic.main.activity_main.*


data class Message(val author: String,val body:String)




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dbHelper = MyDataBaseHelper(this,"main.db",2)
        val db = dbHelper.writableDatabase
            //Greeting(name = "lzq")
        val VideoParentsHelper = VideoDataBaseHelper(this,"test.db",1)
        val db2 = VideoParentsHelper.writableDatabase
        mainButtonTryDataBase.setOnClickListener {
            //val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
            //Toast.makeText(this, "$packageName", Toast.LENGTH_SHORT).show()

        }

        }
    }


