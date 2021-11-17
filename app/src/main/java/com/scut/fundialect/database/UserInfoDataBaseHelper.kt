package com.scut.fundialect.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.widget.Toast
import android.R

import com.google.android.exoplayer2.util.UriUtil
import java.io.File


class UserInfoDataBaseHelper(val context: Context,name:String,version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    //定义包名变量
    val packageName = "com.scut.fundialect"
    public val dbFileName = "userinfo.db"
    private val createUserInfo = "create table userinfo (" +
            " id integer primary key autoincrement," +
            "userNickName text default \"name\"," +
            "userMail text default \"mail\"," +
            "userPassport text default \"password\"," +
            "userSex integer default 1," +
            "userCityId integer default 1," +
            "userSign text default \"sign\"," +
            "userPicFile text default \"${toUriStr(com.scut.fundialect.R.raw.defaultpic)}\"" +
            ")"


    private fun toUriStr(image:Int):String{
        return "android.resource://com.scut.fundialect/$image"
        //default "${toUriStr(com.scut.fundialect.R.raw.defaultpic)}"

    }
    fun initVideoData(db: SQLiteDatabase?){
        val uri = Uri.parse("android.resource://$packageName/raw/sample.png")

    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createUserInfo)
        Toast.makeText(context,"准备载入李子祺数据",Toast.LENGTH_SHORT).show()
        val value1 = ContentValues().apply {
            put("userNickName", "李子祺")
            put("userMail", "1431839116@qq.com")
            put("userPassport", "12345678")
            put("userSex", 1)
            //put("userCityId", 0)

        }
        Toast.makeText(context,"准备载入锅巴数据",Toast.LENGTH_SHORT).show()
        db?.insert("userinfo",null,value1)
        val value2 = ContentValues().apply {
            put("userNickName", "锅巴")
            put("userMail", "1431839116@qq.com")
            put("userPassport", "12345678")
            put("userSex", 1)
            //put("userCityId", 0)

        }
        db?.insert("userinfo",null,value2)



    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}