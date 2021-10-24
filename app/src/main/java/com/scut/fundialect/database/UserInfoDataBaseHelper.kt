package com.scut.fundialect.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.widget.Toast





class UserInfoDataBaseHelper(val context: Context,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    //定义包名变量
    val packageName = "com.scut.fundialect"
    public val dbFileName = "userinfo.db"
    private val createUserInfo = "create table userinfo (" +
            " id integer primary key autoincrement," +
            "userNickName text," +
            "userMail text," +
            "userPassport text," +
            "userSex integer," +
            "userCityId integer," +
            "userSign text," +
            "userPicFile text" +
            ")"



    fun initVideoData(db: SQLiteDatabase?){
        val uri = Uri.parse("android.resource://$packageName/raw/sample.png")

    }
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

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}