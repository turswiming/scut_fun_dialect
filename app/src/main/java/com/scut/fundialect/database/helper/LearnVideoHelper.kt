package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.database.LearnVideoDataBaseHelper

object LearnVideoHelper {
    private  var learnDB: SQLiteDatabase

    init {
        val learnVideoDataBaseHelper = LearnVideoDataBaseHelper(context,"Learn.db",1)
        learnDB =learnVideoDataBaseHelper.writableDatabase
    }
    fun getNextVideo():VideoInfo{
        val videoInfo = VideoInfo(1)
        return videoInfo
    }

    @SuppressLint("Range", "Recycle")
    class VideoInfo(
        id:Int
    ) {
        val videoUri:String
        val videoName:String
        val videoIntroduce:String

        val videoLike:Int
        val videoUploaderId:Int
        val videoCollect:Int
        val videoUpdateTime:Int
        val videoBelongCityId:Int
        val videoIsLiked:Boolean
        val videoIsCollect:Boolean

        init{
            val results = learnDB.query(
                "videoInfo",
                null,
                "id = $id",
                null,
                null,
                null,
                null,
                null)
            results.moveToFirst()
            videoUri = results.getString(results.getColumnIndex("videoUri"))
            videoName = results.getString(results.getColumnIndex("videoName"))
            videoIntroduce = results.getString(results.getColumnIndex("videoIntroduce"))
            videoLike = results.getInt(results.getColumnIndex("videoLike"))
            videoUploaderId = results.getInt(results.getColumnIndex("videoUploaderId"))
            videoCollect = results.getInt(results.getColumnIndex("videoCollect"))
            videoUpdateTime = results.getInt(results.getColumnIndex("videoUpdateTime"))
            videoBelongCityId = results.getInt(results.getColumnIndex("videoBelongCityId"))
            videoIsLiked = toBool(results.getInt(results.getColumnIndex("videoIsLiked")))
            videoIsCollect = toBool(results.getInt(results.getColumnIndex("videoIsCollect")))
            results.close()

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
}