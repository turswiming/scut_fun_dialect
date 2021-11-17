package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import com.scut.fundialect.MyApplication
import com.scut.fundialect.database.UserInfoDataBaseHelper

object UserHelpr {
    private  var userDB: SQLiteDatabase

    init {
        val userInfoDataBaseHelper = UserInfoDataBaseHelper(MyApplication.context,"userinfo.db",1)
        userDB =userInfoDataBaseHelper.writableDatabase
    }

    @SuppressLint("Range")
    class UserInfo(userId: Int){
        val userNickName:String
        val userMail:String
        val userPassport:String
        val userSign:String
        val userPicFile:String


        val userSex:Boolean
        val userCityId:Int


        init{
            val results = userDB.query(
                "commentInfo",
                null,
                "id = $userId",
                null,
                null,
                null,
                null,
                null)
            results.moveToFirst()
            userNickName = results.getString(results.getColumnIndex("userNickName"))
            userMail = results.getString(results.getColumnIndex("userMail"))
            userPassport = results.getString(results.getColumnIndex("userPassport"))
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