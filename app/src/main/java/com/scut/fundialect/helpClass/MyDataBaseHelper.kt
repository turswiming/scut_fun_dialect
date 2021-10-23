package com.scut.fundialect.helpClass

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
enum class sex{
    male,
    female
}
class MyDataBaseHelper(val context: Context,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    private val createUserInfo = "create table userinfo (" +
            " id integer primary key autoincrement," +
            "userNickName text," +
            "userMail text," +
            "userPassport text," +
            "userSex integer," +
            "userCityId integer" +
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUserInfo)
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show()

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}