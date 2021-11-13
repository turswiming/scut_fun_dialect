package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.scut.fundialect.MyApplication
import com.scut.fundialect.database.CityDataBaseHelper

object DubedVideoHelper {
    init {
        val cityDataBaseHelper = CityDataBaseHelper(MyApplication.context,"city.db",1)
        val cityDB = cityDataBaseHelper.writableDatabase
    }
    const val tableName = "DubedVideo"
    //这一行的意思是屏蔽报错......绝了

}