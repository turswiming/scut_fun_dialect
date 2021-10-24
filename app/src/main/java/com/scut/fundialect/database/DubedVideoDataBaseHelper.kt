package com.scut.fundialect.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DubedVideoDataBaseHelper(context: Context, name:String, version:Int):
    VideoDataBaseHelper(context,name,version)  {
    override fun onCreate(db: SQLiteDatabase?) {
        super.onCreate(db)
    }
    //这一行的意思是屏蔽报错......绝了
    @SuppressLint("Range")
    fun convertdata(fromData:SQLiteDatabase, id:Int, thisData:SQLiteDatabase){
        val results =  fromData.query("videoInfo",
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
                // 遍历Cursor对象，取出数据
                videoFileName = results.getString(results.getColumnIndex("videoFileName"))
                videoName = results.getString(results.getColumnIndex("videoName"))
                videoBelongCityId = results.getInt(results.getColumnIndex("videoBelongCityId"))
            } while (results.moveToNext())
            val value1= ContentValues().apply {
                put("videoFileName",videoFileName)
                put("videoName",videoName)
                put("videoBelongCityId",videoBelongCityId)
            }
            thisData?.insert("videoInfo",null,value1)
        }
        results.close()
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
    }
}