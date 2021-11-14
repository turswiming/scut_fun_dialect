package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.database.CityDataBaseHelper
import java.lang.Exception

object CityHelper {
    private  var cityDB:SQLiteDatabase
    init {
        val cityDataBaseHelper = CityDataBaseHelper(context,"city.db",1)
         cityDB = cityDataBaseHelper.writableDatabase
    }
    private const val tableName = "city"
    @SuppressLint("Range")
    fun getCityId(CityName:String):Int{
        val results = cityDB.query(tableName,
            null,
            "cityname = $CityName",
            null,
            null,
            null,
            null,
            null)
        results.moveToFirst()
        val cityId = results.getInt(results.getColumnIndex("id"))
        results?.close()
        return cityId
    }
    @SuppressLint("Range")
    fun getCityName(cityId:Int):String{
        //Toast.makeText(context, "进入查询函数", Toast.LENGTH_SHORT).show()
        try {
            val results = cityDB.query(
                tableName,
                null,
                "id = $cityId",
                null,
                null,
                null,
                null,
                null)
            val index = results.getColumnIndex("cityname")
            //Toast.makeText(context, "获取查询结果"+index, Toast.LENGTH_SHORT).show()
            //将指针移到第一行
            results.moveToFirst()
            val cityName = results.getString(index)
            //Toast.makeText(context, "准备关闭", Toast.LENGTH_SHORT).show()
            results.close()
            //Toast.makeText(context, "准备返回", Toast.LENGTH_SHORT).show()

            return cityName
        } catch (e:Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()

            return e.localizedMessage
        }


    }

    @SuppressLint("Range")
    fun getParentCity(thisId:Int) : CityDataBaseHelper.CityData {
        val results = cityDB.query(tableName,
            null,
            "where id = $thisId",
            null,
            null,
            null,
            null,
            null)
        val parientId = results.getInt(results.getColumnIndex("pid"))
        results?.close()
        val results2 = cityDB.query(tableName,
            null,
            "where id = $parientId",
            null,
            null,
            null,
            null,
            null)
        val CityName = results2.getString(results2.getColumnIndex("cityname"))
        results2?.close()
        val out = CityDataBaseHelper.CityData(parientId, CityName)
        return out
    }
    @SuppressLint("Range")
    fun getParentCity(thisCityName:String) : CityDataBaseHelper.CityData {
        val results = cityDB.query(tableName,
            null,
            "where cityname = $thisCityName",
            null,
            null,
            null,
            null,
            null)
        val parientId = results.getInt(results.getColumnIndex("pid"))
        results?.close()
        val results2 = cityDB.query(tableName,
            null,
            "where id = $parientId",
            null,
            null,
            null,
            null,
            null)
        val CityName = results2.getString(results2.getColumnIndex("cityname"))
        results2?.close()
        val out = CityDataBaseHelper.CityData(parientId, CityName)
        return out
    }
    @SuppressLint("Range")
    fun getChildCity(thisCityName:String) :MutableList<CityDataBaseHelper.CityData>{
        val results = cityDB.query(tableName,
            null,
            "where cityname = $thisCityName",
            null,
            null,
            null,
            null,
            null)
        val childPid = results?.getInt(results.getColumnIndex("id"))
        results?.close()
        val list: MutableList<CityDataBaseHelper.CityData> = mutableListOf<CityDataBaseHelper.CityData>()
        val results2 =  cityDB.query(tableName,
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
                val item = CityDataBaseHelper.CityData(cityID, cityName)
                list.add(item)
            } while (results2.moveToNext())
        }
        results2.close()
        return list
    }
}