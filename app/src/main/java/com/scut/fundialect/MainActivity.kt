package com.scut.fundialect

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.scut.fundialect.helpClass.MyDataBaseHelper
import kotlinx.android.synthetic.main.activity_main.*


data class Message(val author: String,val body:String)




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val dbHelper = MyDataBaseHelper(this,"main.db",1)
        val db = dbHelper.writableDatabase
            //Greeting(name = "lzq")

        mainButtonTryDataBase.setOnClickListener {
            dbHelper.initCityData(db)
            val value1 = ContentValues().apply {
                put("userNickName", "The Da Vinci Code")
                put("userMail", "Dan Brown@qq.com")
                put("userPassport", "3423423")
                put("userSex", 1)
                put("userCityId", 0)

            }
            db.insert("userinfo",null,value1)
        }

        }
    }


