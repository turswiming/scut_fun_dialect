package com.scut.fundialect.helpClass

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class VideoDataBaseHelper(val context: Context, name:String, version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    val initVideoDatabase = "create table videoInfo (" +
            "id integer primary key autoincrement," +
            "videoFileName text," +
            "videoName text," +
            "videoLike integer," +
            "videoIsLiked integer," +
            "videoCollect integer," +
            "videoIsCollect integer," +
            "videoIntroduce text," +
            "videoBelongCityId integer" +
            ")"
    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}