package com.scut.fundialect.helpClass

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LearnVideoDataBaseHelper( context: Context, name:String, version:Int):
    VideoDataBaseHelper(context,name,version) {
    override fun onCreate(db: SQLiteDatabase?) {
        super.onCreate(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
    }
}