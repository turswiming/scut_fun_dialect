package com.scut.fundialect.helpClass

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.scut.fundialect.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.AsynchronousServerSocketChannel.open
import java.nio.channels.DatagramChannel.open
import java.nio.channels.Pipe.open
import java.nio.charset.Charset


enum class sex{
    male,
    female
}
class MyDataBaseHelper(val context: Context,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    private val createUserInfo = "create table userinfo (" +
            " id integer primary key autoincrement," +
            "userNickName text," +
            "userMail text," +
            "userPassport text," +
            "userSex integer," +
            "userCityId integer" +
            ")"
    val path = ""

    fun initCityData(db: SQLiteDatabase?){
        //var nextLineExists = true
        //读取文件,myList为一个数组，每个子项均为一行的字符串
        val fileName = "citydata.sql"



        val input = context.assets.open(fileName)
        var content = input.readBytes().toString(Charset.defaultCharset())
        val contex2 = content.split("\n")
        //db?.execSQL("CREATE TABLE city (  id integer primary key autoincrement,  pid integer,  cityname text,  type integer)")
        CoroutineScope(Dispatchers.Default).launch {

            contex2.forEach{
                //Thread.sleep(1000)
                //Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                db?.execSQL(it.substring(0,it.length-1))

            }
            Toast.makeText(context, "完成城市数据库初始化", Toast.LENGTH_SHORT).show()
        }

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUserInfo)
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show()
        val value1 = ContentValues().apply {
            put("userNickName", "李子祺")
            put("userMail", "1431839116@qq.com")
            put("userPassport", "12345678")
            put("userSex", 1)
            //put("userCityId", 0)

        }
        db?.insert("userinfo",null,value1)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}