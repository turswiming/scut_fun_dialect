package com.scut.fundialect.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset

class CityDataBaseHelper(val context: Context, name:String, version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    class CityData(cityId:Int,cityName:String){
        var name:String = cityName
        fun getTheName():String{
            return name
        }
    }
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
            Toast.makeText(context, "完成城市数据库初始化", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        initCityData(db)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


}