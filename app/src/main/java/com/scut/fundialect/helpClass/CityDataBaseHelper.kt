package com.scut.fundialect.helpClass

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.charset.Charset

class CityDataBaseHelper(val context: Context, name:String, version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    inner class CityData(cityId:Int,cityName:String)
    val tableName = "city"
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

    override fun onCreate(db: SQLiteDatabase?) {
        initCityData(db)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    @SuppressLint("Range")
    fun getCityId(db: SQLiteDatabase,CityName:String):Int{
        val results = db.query(tableName,
            null,
            "where cityname = $CityName",
            null,
            null,
            null,
            null,
            null)
        val cityId = results.getInt(results.getColumnIndex("id"))
        results?.close()
        return cityId
    }
    @SuppressLint("Range")
    fun getParentCity(db: SQLiteDatabase,thisId:Int) :CityData {
        val results = db.query(tableName,
            null,
            "where id = $thisId",
            null,
            null,
            null,
            null,
            null)
        val parientId = results.getInt(results.getColumnIndex("pid"))
        results?.close()
        val results2 = db.query(tableName,
            null,
            "where id = $parientId",
            null,
            null,
            null,
            null,
            null)
        val CityName = results2.getString(results2.getColumnIndex("cityname"))
        results2?.close()
        val out = CityData(parientId,CityName)
        return out
    }
    @SuppressLint("Range")
    fun getParentCity(db: SQLiteDatabase,thisCityName:String) :CityData {
        val results = db.query(tableName,
            null,
            "where cityname = $thisCityName",
            null,
            null,
            null,
            null,
            null)
        val parientId = results.getInt(results.getColumnIndex("pid"))
        results?.close()
        val results2 = db.query(tableName,
            null,
            "where id = $parientId",
            null,
            null,
            null,
            null,
            null)
        val CityName = results2.getString(results2.getColumnIndex("cityname"))
        results2?.close()
        val out = CityData(parientId,CityName)
        return out
    }
    @SuppressLint("Range")
    fun getChildCity(db:SQLiteDatabase,thisCityName:String) :MutableList<CityData>{
        val results = db.query(tableName,
            null,
            "where cityname = $thisCityName",
            null,
            null,
            null,
            null,
            null)
        val childPid = results?.getInt(results.getColumnIndex("id"))
        results?.close()
        val list: MutableList<CityData> = mutableListOf<CityData>()
        val results2 =  db.query(tableName,
            null,
            "where pid = $childPid",
            null,
            null,
            null,
            null,
            null)
        if (results2.moveToFirst()) {
            do {

                // 遍历Cursor对象，取出数据并打印
                val cityID = results2.getInt(results2.getColumnIndex("id"))
                val cityName = results2.getString(results2.getColumnIndex("cityname"))
                val item =  CityData(cityID,cityName)
                list.add(item)
            } while (results2.moveToNext())
        }
        results2.close()
        return list
    }
}