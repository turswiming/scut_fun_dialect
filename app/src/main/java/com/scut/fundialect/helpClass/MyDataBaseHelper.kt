package com.scut.fundialect.helpClass

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.File

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
    val path = "src/main/res/sqlData"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUserInfo)
        Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show()
        val value1 = ContentValues().apply {
            put("userNickName", "李子祺")
            put("userMail", "1431839116@qq.com")
            put("userPassport", "12345678")
            put("userSex", 1)
            //put("userCityId", 0)

        }
        db?.insert("userinfo",null,value1)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}