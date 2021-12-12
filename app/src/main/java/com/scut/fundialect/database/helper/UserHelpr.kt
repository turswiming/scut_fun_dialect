package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.scut.fundialect.MyApplication
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.database.UserInfoDataBaseHelper

object UserHelpr {
    private  var userDB: SQLiteDatabase

    init {
        val userInfoDataBaseHelper = UserInfoDataBaseHelper(MyApplication.context,"userinfo.db",1)
        userDB =userInfoDataBaseHelper.writableDatabase
    }
    var userNow = 1;
    @SuppressLint("Range")
    class UserInfo(userId: Int){
        val userNickName:String
        val userMail:String
        val userPassport:String
        val userSign:String
        public val userPicFile:String


        val userSex:Boolean
        val userCityId:Int


        init{
//            Toast.makeText(context,"准备查询",Toast.LENGTH_SHORT).show()

            val results = userDB.query(
                "userinfo",
                null,
                "id = $userId",
                null,
                null,
                null,
                null,
                null)
            results.moveToFirst()
//            Toast.makeText(context,"准备获取昵称",Toast.LENGTH_SHORT).show()
            userNickName = results.getString(results.getColumnIndex("userNickName"))
            userMail = results.getString(results.getColumnIndex("userMail"))
            userPassport = results.getString(results.getColumnIndex("userPassport"))
//            Toast.makeText(context,"准备获取用户签名",Toast.LENGTH_SHORT).show()
            userSign = results.getString(results.getColumnIndex("userSign"))
            userPicFile = results.getString(results.getColumnIndex("userPicFile"))

            userCityId = results.getInt(results.getColumnIndex("userCityId"))

            userSex = toBool(results.getInt(results.getColumnIndex("userSex")))
            results.close()

        }
    }

    private fun toBool(int: Int):Boolean{
        if (int==0){
            return false
        }
        return true
    }
    fun toInt(bool:Boolean):Int{
        if(bool){
            return 1
        }
        return 0
    }
}