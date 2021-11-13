package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.scut.fundialect.MyApplication
import com.scut.fundialect.database.CityDataBaseHelper
import com.scut.fundialect.database.VideoDataBaseHelper

object VideoHelper {
    private  var videoDB: SQLiteDatabase
    init {
        val videoDataBaseHelper = VideoDataBaseHelper(MyApplication.context,"video.db",1)
        videoDB = videoDataBaseHelper.writableDatabase
    }
    @SuppressLint("Range")
    fun convertdataDeafult(id:Int,fromData: SQLiteDatabase){
        val results =  fromData.query(
            "videoInfo",
            null,
            "where id = $id",
            null,
            null,
            null,
            null,
            null)
        var videoFileName:String =""
        var videoName:String=""
        var videoBelongCityId:Int = 0
        if (results.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                videoFileName = results.getString(results.getColumnIndex("videoFileName"))
                videoName = results.getString(results.getColumnIndex("videoName"))
                videoBelongCityId = results.getInt(results.getColumnIndex("videoBelongCityId"))
            } while (results.moveToNext())
            val value1= ContentValues().apply {
                put("videoFileName",videoFileName)
                put("videoName",videoName)
                put("videoBelongCityId",videoBelongCityId)
            }
            videoDB.insert("videoInfo",null,value1)
        }
        results.close()
    }
}