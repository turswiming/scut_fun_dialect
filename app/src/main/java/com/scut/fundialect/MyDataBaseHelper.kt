package com.scut.fundialect

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDataBaseHelper(val context: Context,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    private val creatBook ="crate table book" +
            "id integer primary key autoincrement" +
            "userAccessId text" +
            "userPassword text" +
            "userEmail text" +
            "userPhoneNumber integer"
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(creatBook)
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show()

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}