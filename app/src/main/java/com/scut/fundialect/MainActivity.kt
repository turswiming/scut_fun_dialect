package com.scut.fundialect


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.scut.fundialect.database.UserInfoDataBaseHelper

import com.scut.fundialect.database.VideoDataBaseHelper
import kotlinx.android.synthetic.main.activity_main.*


data class Message(val author: String,val body:String)




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dbHelper = UserInfoDataBaseHelper(this,"userinfo.db",2)
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


