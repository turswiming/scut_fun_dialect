package com.scut.fundialect.helpClass

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.scut.fundialect.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset




class MyDataBaseHelper(val context: Context,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    //定义包名变量
    val packageName = "com.scut.fundialect"
    private val createUserInfo = "create table userinfo (" +
            " id integer primary key autoincrement," +
            "userNickName text," +
            "userMail text," +
            "userPassport text," +
            "userSex integer," +
            "userCityId integer," +
            "userSign text," +
            "userPicFile text" +
            ")"


    fun initCityData(db: SQLiteDatabase?){
        //这是一段屎山代码
        //包括
        //60000字长的字符串
        //对上面的字符串做split操作
        //循环执行3000次数据库操作
        //以及暴力协程
        //请程序员自备降压药，谢谢。
        val fileName = "citydata.sql"
        val input = context.assets.open(fileName)
        var content = input.readBytes().toString(Charset.defaultCharset())
        val contex2 = content.split("\n")
        //创建协程，让这段代码慢慢跑。
        //这段代码要跑10秒左右，手机可遭不住。
        CoroutineScope(Dispatchers.Default).launch {
            contex2.forEach{
                db?.execSQL(it.substring(0,it.length-1))

            }
            //Toast.makeText(context, "完成城市数据库初始化", Toast.LENGTH_SHORT).show()
        }

    }

    fun initVideoData(db: SQLiteDatabase?){
        val uri = Uri.parse("android.resource://$packageName/raw/sample.png")

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

        initCityData(db)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    if(oldVersion<=1){
        initCityData(db)
    }
    }
}