package com.scut.fundialect.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Looper
import android.widget.Toast
import com.scut.fundialect.database.helper.Public
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
        /**
        //这是一段屎山代码
        //包括
        //60000字长的字符串
        //对上面的字符串做split操作
        //循环执行3000次数据库操作
        //以及暴力协程
        //请程序员自备降压药，谢谢。
        */
        val fileName = "citydata.sql"
        Toast.makeText(context, "获取文件流", Toast.LENGTH_SHORT).show()
        val input = context.assets.open(fileName)
        Toast.makeText(context, "对字符串做处理", Toast.LENGTH_SHORT).show()
        var content = input.readBytes().toString(Charset.defaultCharset())
        val contex2 = content.split("\n")
        Toast.makeText(context, "准备逐句执行", Toast.LENGTH_SHORT).show()
        contex2.forEach{
            db?.execSQL(it.substring(0,it.length-1))

        }
        Toast.makeText(context, "完成城市数据库初始化", Toast.LENGTH_SHORT).show()
        //创建协程，让这段代码慢慢跑。
        //这段代码要跑10秒左右，手机可遭不住。
//        CoroutineScope(Dispatchers.Default).launch {
//
//        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        Toast.makeText(context, "准备初始化城市数据", Toast.LENGTH_SHORT).show()
        initCityData(db)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


}