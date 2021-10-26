package com.scut.fundialect.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import com.scut.fundialect.R
import java.util.*

open class VideoDataBaseHelper(val context: Context, name:String, version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    val initVideoDatabase = "create table videoInfo (" +
            "id integer primary key autoincrement," +
            "videoUri text," +
            "videoName text," +
            "videoLike integer," +
            "videoUploaderId integer," +
            "videoIsLiked integer," +
            "videoCollect integer," +
            "videoIsCollect integer," +
            "videoIntroduce text," +
            "videoUpdateTime integer," +
            "videoPicUri text, " +
            "videoBelongCityId integer" +
            ")"
    val initCommetDatabase = "create table commentInfo (" +
            "id integer primary key autoincrement," +
            "parentId integer," +//指向视频
            "commenterId integer," +
            "comment text," +
            "commentTime text," +
            "isLiked integer," +
            "numberLiked integer" +
            ")"
    //定义包名变量
    val packageName = "com.scut.fundialect"
    val DatePattern = "yyyy-MM-dd HH:mm:ss.SSS"
    fun initVideoDatabase(db: SQLiteDatabase?){
        val value1 = ContentValues().apply {
//            put("videoUri", Uri.parse( "android.resource://" + getPackageName() + "/" + R.raw.video1))
            put("videoName", "欣赏黑色")
            put("videoLike", 342)
            put("videoIsLiked", 0)
            put("videoCollect", 134)
            put("videoIsCollect", 1)
            put("videoIntroduce", "这是关于一个黑色的故事")
            //SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
            put("videoUpdateTime", Date().time -300000000)
            put("videoBelongCityId", 1)

        }
        db?.insert("videoInfo",null,value1)
        val value2 = ContentValues().apply {
            put("videoFileName", "video2.mp4")
            put("videoName", "欣赏蓝色")
            put("videoLike", 3420)
            put("videoIsLiked", 1)
            put("videoCollect", 1304)
            put("videoIsCollect", 0)
            put("videoIntroduce", "这是关于一个蓝色的故事")
            put("videoUpdateTime", Date().time -600000000)
            put("videoBelongCityId", 1)

        }
        db?.insert("videoInfo",null,value2)
        val value3 = ContentValues().apply {
            put("videoFileName", "video1.mp4")
            put("videoName", "欣赏橙色")
            put("videoLike", 1342)
            put("videoIsLiked", 1)
            put("videoCollect", 134)
            put("videoIsCollect", 1)
            put("videoIntroduce", "这是关于一个橙色的故事")
            put("videoUpdateTime", Date().time -60000000)
            put("videoBelongCityId", 1)

        }
        db?.insert("videoInfo",null,value3)
        val value4 = ContentValues().apply {
            put("videoFileName", "video1.mp4")
            put("videoName", "欣赏绿色")
            put("videoLike", 342)
            put("videoIsLiked", 0)
            put("videoCollect", 134)
            put("videoIsCollect", 2)
            put("videoIntroduce", "这是关于一个绿色的故事")
            put("videoUpdateTime", Date().time -6000000)
            put("videoBelongCityId", 1)

        }
        db?.insert("videoInfo",null,value4)

    }
    fun initCommentInfoDatabase(db: SQLiteDatabase?){
        repeat(1000){
            val value1 = ContentValues().apply {
                put("parentId", (0..3).random())
                put("commenterId", 1)
                put("comment", "太哲学了，"+(2..5).random()+"简直是我看过最好看的")
                put("commentTime", Date().time -100000*(0..100).random())
                put("isLiked", (0..1).random())
                put("numberLiked", (0..1000).random())


            }
            db?.insert("commentInfo",null,value1)
        }
        repeat(3000){
            val value1 = ContentValues().apply {
                put("parentId", (0..3).random())
                put("commenterId", 1)
                put("comment", "啥玩意儿啊，"+(2..5).random()+"垃圾回收站也不过如此")
                put("commentTime", Date().time -100000*(0..100).random())
                put("isLiked", (0..1).random())
                put("numberLiked", (0..1000).random())


            }
            db?.insert("commentInfo",null,value1)
        }

    }

    //这一行的意思是屏蔽报错......绝了
    @SuppressLint("Range")
    fun convertdataDeafult(fromData:SQLiteDatabase, id:Int, thisData:SQLiteDatabase){
        val results =  fromData.query(
            "videoInfo",
            null,
            "where id = $id",
            null,
            null,
            null,
            null,
            null)
        var videoFileName:String =""
        var videoName:String=""
        var videoBelongCityId:Int = 0
        if (results.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                videoFileName = results.getString(results.getColumnIndex("videoFileName"))
                videoName = results.getString(results.getColumnIndex("videoName"))
                videoBelongCityId = results.getInt(results.getColumnIndex("videoBelongCityId"))
            } while (results.moveToNext())
            val value1= ContentValues().apply {
                put("videoFileName",videoFileName)
                put("videoName",videoName)
                put("videoBelongCityId",videoBelongCityId)
            }
            thisData.insert("videoInfo",null,value1)
        }
        results.close()
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(initVideoDatabase)
        initVideoDatabase(db)

        db?.execSQL(initCommetDatabase)
        initCommentInfoDatabase(db)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}