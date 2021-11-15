package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.scut.fundialect.MyApplication
import com.scut.fundialect.database.CityDataBaseHelper
import com.scut.fundialect.database.DubedVideoDataBaseHelper

object DubedVideoHelper {
    init {
        val dubedVideoDataBaseHelper = DubedVideoDataBaseHelper(MyApplication.context,"city.db",1)
        val dubedDB = dubedVideoDataBaseHelper.writableDatabase
    }
    const val tableName = "DubedVideo"
    //这一行的意思是屏蔽报错......绝了

}