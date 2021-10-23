package com.scut.fundialect.helpClass

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class ModelVideoDataBaseHelper(context: Context, name:String, version:Int):
    VideoDataBaseHelper(context,name,version)  {
    override fun onCreate(db: SQLiteDatabase?) {
        super.onCreate(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
    }
}