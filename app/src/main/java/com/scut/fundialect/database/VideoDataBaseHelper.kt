package com.scut.fundialect.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.os.Looper
import android.widget.Toast
import com.scut.fundialect.R
import com.scut.fundialect.database.helper.Public
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

open class VideoDataBaseHelper(val context: Context, name:String, version:Int):
    SQLiteOpenHelper(context,name,null,version) {
    val  videoTableName = "videoInfo"
    val commentTableName ="commentInfo"
    val initVideoDatabase = "create table "+videoTableName+" (" +
            "id integer primary key autoincrement," +
            "videoUri text default \"android.resource://${context.packageName}/${R.raw.video1}\"," +
            "videoName text default \"未命名视频\"," +
            "videoLike integer default ${(2..50).random()}," +
            "videoUploaderId integer default ${(1..2).random()}," +
            "videoIsLiked integer default ${(0..1).random()}," +
            "videoCollect integer default ${(0..10).random()}," +
            "videoIsCollect integer default ${(0..1).random()}," +
            "videoIntroduce text default \"上传的人很懒，什么都没写\"," +
            "videoUpdateTime integer default \"上传的人很懒，什么都没写\"," +
            "videoPicUri text default \"android.resource://${context.packageName}/${R.raw.defaultpic}\"," +
            "videoBelongCityId integer  default 1" +
            ")"
    val initCommetDatabase = "create table "+commentTableName+" (" +
            "id integer primary key autoincrement," +
            "parentId integer," +//指向视频
            "commenterId integer," +
            "comment text," +
            "commentTime text," +
            "isLiked integer," +
            "numberLiked integer" +
            ")"
    private fun toUriStr(image:Int):String{
        return "android.resource://com.scut.fundialect/$image"
        //default "${toUriStr(com.scut.fundialect.R.raw.defaultpic)}"

    }
    //定义包名变量
    val packageName = "com.scut.fundialect"
    val DatePattern = "yyyy-MM-dd HH:mm:ss.SSS"
    fun initVideoDatabase(db: SQLiteDatabase?){
        repeat(5){
            val value1 = ContentValues().apply {
                put("videoUri","android.resource://"+ context.packageName +"/"+ R.raw.video1)
                put("videoName", "欣赏黑色")
                put("videoLike", 342)
                put("videoIsLiked", 0)
                put("videoCollect", 134)
                put("videoIsCollect", 0)
                put("videoPicUri","${toUriStr(R.raw.defaultpic)}")
                put("videoIntroduce", "这是关于一个黑色的故事")
                //SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                put("videoUpdateTime", Date().time -300000000)
                put("videoBelongCityId", 1)
            }
            db?.insert("videoInfo",null,value1)
            val value2 = ContentValues().apply {
                put("videoUri","android.resource://"+ context.packageName +"/"+ R.raw.video2)
                put("videoName", "欣赏蓝色")
                put("videoLike", 3420)
                put("videoIsLiked", 1)
                put("videoCollect", 1304)
                put("videoPicUri","${toUriStr(R.raw.defaultpic)}")

                put("videoIsCollect", 0)
                put("videoIntroduce", "这是关于一个蓝色的故事")
                put("videoUpdateTime", Date().time -600000000)
                put("videoBelongCityId", 1)

            }
            db?.insert("videoInfo",null,value2)
            val value3 = ContentValues().apply {
                put("videoUri","android.resource://"+ context.packageName +"/"+ R.raw.video3)

                put("videoName", "欣赏橙色")
                put("videoLike", 1342)
                put("videoIsLiked", 1)
                put("videoCollect", 134)
                put("videoPicUri","${toUriStr(com.scut.fundialect.R.raw.defaultpic)}")

                put("videoIsCollect", 0)
                put("videoIntroduce", "这是关于一个橙色的故事")
                put("videoUpdateTime", Date().time -60000000)
                put("videoBelongCityId", 1)

            }
            db?.insert("videoInfo",null,value3)
            val value4 = ContentValues().apply {
                put("videoUri","android.resource://"+ context.packageName +"/"+ R.raw.video4)

                put("videoName", "欣赏绿色")
                put("videoLike", 342)
                put("videoIsLiked", 0)
                put("videoCollect", 134)
                put("videoIsCollect", 1)
                put("videoPicUri","${toUriStr(com.scut.fundialect.R.raw.defaultpic)}")

                put("videoIntroduce", "这是关于一个绿色的故事")
                put("videoUpdateTime", Date().time -6000000)
                put("videoBelongCityId", 1)

            }
            db?.insert("videoInfo",null,value4)

        }


    }
    fun initCommentInfoDatabase(db: SQLiteDatabase?){
        repeat(10){
            val value1 = ContentValues().apply {
                put("parentId", (1..3).random())
                put("commenterId", 2)
                put("comment", "太哲学了，"+(2..50).random()+"简直是我看过最好看的")
                put("commentTime", Date().time -100000*(0..100).random())
                put("isLiked", (0..1).random())
                put("numberLiked", (0..1000).random())


            }
            db?.insert("commentInfo",null,value1)
        }
        repeat(30){
            val value1 = ContentValues().apply {
                put("parentId", (1..3).random())
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

    override fun onCreate(db: SQLiteDatabase?) {
        CoroutineScope(Dispatchers.Default).launch {
            db?.execSQL(initVideoDatabase)
            initVideoDatabase(db)

            db?.execSQL(initCommetDatabase)
            initCommentInfoDatabase(db)
            Public.finishDatainit()
            Looper.prepare();
            Toast.makeText(context,"完成一个视频的数据载入", Toast.LENGTH_SHORT).show()
            Looper.loop();

        }


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}