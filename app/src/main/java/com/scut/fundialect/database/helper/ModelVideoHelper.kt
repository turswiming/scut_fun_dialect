package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.scut.fundialect.MyApplication
import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.R
import com.scut.fundialect.database.ModelVideoDataBaseHelper
import com.scut.fundialect.database.helper.ModelVideoHelper.cathe
import kotlinx.coroutines.*
import java.lang.Thread.sleep

object ModelVideoHelper {

    @SuppressLint("StaticFieldLeak")
    val modelVideoDataBaseHelper = ModelVideoDataBaseHelper(
        context,
        "Model.db",
        1
    )
    var modelDB: SQLiteDatabase=modelVideoDataBaseHelper.writableDatabase
    var cathe:List<ModelVideoCathe> =  getTheCathe()
    fun getCommitNumber(videoId: Int):Int {
        return 666
    }
    var lastShowedVideo = 0
    /**
     * 自动获取下一个视频。
     * 视频内容不会重样
     * 所有查询语句都封装好了，你一点都看不着。
     * **/
    fun getNextVideo(): ModelVideoInfo {
        try {
            return ModelVideoInfo(++lastShowedVideo)

        } catch (e: Exception) {
            lastShowedVideo =1
            return ModelVideoInfo(1)
        }
    }
    @SuppressLint("Range")
    fun getVideoComment(videoId: Int):List<CommentInfo> {
        val results = modelDB.query(
            "commentInfo",
            null,
            "parentId = $videoId",
            null,
            null,
            null,
            null)
        var commentInfoList: MutableList<CommentInfo> = mutableListOf<CommentInfo>()


        if (results.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                val id = results.getInt(results.getColumnIndex("id"))
                commentInfoList+= CommentInfo(id)

            } while (results.moveToNext())
        }
        results.close()


        return commentInfoList
    }
    @SuppressLint("Range")
    class CommentInfo(CommitId: Int){
        val CommitId = CommitId
        val comment:String
        val commentTime:String

        val numberLiked:Int
        val commenterId:Int


        val isLiked:Boolean

        init{
            val results = modelDB.query(
                "commentInfo",
                null,
                "id = $CommitId",
                null,
                null,
                null,
                null,
                null)
            results.moveToFirst()
            comment = results.getString(results.getColumnIndex("comment"))
            commentTime = results.getString(results.getColumnIndex("commentTime"))

            numberLiked = results.getInt(results.getColumnIndex("numberLiked"))
            commenterId = results.getInt(results.getColumnIndex("commenterId"))

            isLiked = toBool(results.getInt(results.getColumnIndex("isLiked")))
            results.close()

        }
    }

 @SuppressLint("Range")
 fun getTheCathe():List<ModelVideoCathe>{
     Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
             val results = modelDB.query(
            "videoInfo",
            null,
            null,
            null,
            null,
            null,
            null,
            null)
     Toast.makeText(context,"准备读取数据库",Toast.LENGTH_SHORT).show()
     var modelVideoCathe: MutableList<ModelVideoCathe> = mutableListOf()

     if (results.moveToFirst()) {
         do {
             val videoId = results.getInt(results.getColumnIndex("id"))

             val videoUri = results.getString(results.getColumnIndex("videoUri"))
             val    videoName = results.getString(results.getColumnIndex("videoName"))
             val    videoIntroduce = results.getString(results.getColumnIndex("videoIntroduce"))
             val    videoPicUri = results.getString(results.getColumnIndex("videoPicUri"))

             val    videoLike = results.getInt(results.getColumnIndex("videoLike"))
             val    videoUploaderId = results.getInt(results.getColumnIndex("videoUploaderId"))
             val    videoCollect = results.getInt(results.getColumnIndex("videoCollect"))
             val    videoUpdateTime = results.getInt(results.getColumnIndex("videoUpdateTime"))
             val    videoBelongCityId = results.getInt(results.getColumnIndex("videoBelongCityId"))
             val    videoIsLiked = toBool(results.getInt(results.getColumnIndex("videoIsLiked")))
             val    videoIsCollect = toBool(results.getInt(results.getColumnIndex("videoIsCollect")))
             // 遍历Cursor对象，取出数据并打印
             modelVideoCathe += ModelVideoCathe(
                 videoId,
                 videoUri,
                 videoName,
                 videoIntroduce,
                 videoPicUri,
                 videoLike,
                 videoUploaderId,
                 videoCollect,
                 videoUpdateTime,
                 videoBelongCityId,
                 videoIsLiked,
                 videoIsCollect

             )

         } while (results.moveToNext())
     }
     results.close()
        Toast.makeText(context,"准备返回",Toast.LENGTH_SHORT).show()
     return modelVideoCathe
 }

    @SuppressLint("Range")
    fun switchCommentLike(commitId: Int) {
//        Toast.makeText(context,"即将查询",Toast.LENGTH_SHORT).show()

        val results = modelDB.query(
            "commentInfo",
            null,
            "id = $commitId",
            null,
            null,
            null,
            null,
            null)
        results.moveToFirst()
//        Toast.makeText(context,"即将解析表格行数",Toast.LENGTH_SHORT).show()
        val isLiked = results.getInt(results.getColumnIndex("isLiked"))
        var numberLiked = results.getInt(results.getColumnIndex("numberLiked"))
        if (isLiked==0){
            numberLiked+=1
        }else{
            numberLiked-=1
        }
        results.close()
        val values = ContentValues()
        //下面这句话那个三层嵌套函数意思是，把数字转成bool再取反，再转成数字。我懒得写lambda了
        values.put("isLiked", toInt(!toBool(isLiked)))
        values.put("numberLiked",numberLiked)
//        Toast.makeText(context,"即将进行改表",Toast.LENGTH_SHORT).show()
        modelDB.update(
            "commentInfo",
            values,
            "id = $commitId",
            null)
//        Toast.makeText(context,"改表完成,准备返回",Toast.LENGTH_SHORT).show()

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
    @SuppressLint("Range")
    fun switchLike(videoId: Int) {
        //Toast.makeText(context,"即将进行查询",Toast.LENGTH_SHORT).show()
        val results = modelDB.query(
            "videoInfo",
            null,
            "id = $videoId",
            null,
            null,
            null,
            null,
            null)
        results.moveToFirst()
        //Toast.makeText(context,"即将解析表格行数",Toast.LENGTH_SHORT).show()
        val videoIsLiked = results.getInt(results.getColumnIndex("videoIsLiked"))
        results.close()
        val values = ContentValues()
        //下面这句话那个三层嵌套函数意思是，把数字转成bool再取反，再转成数字。我懒得写lambda了
        values.put("videoIsLiked", toInt(!toBool(videoIsLiked)))
        //Toast.makeText(context,"即将进行改表",Toast.LENGTH_SHORT).show()
        modelDB.update(
            "videoInfo",
            values,
            "id = $videoId",
            null)

    }
    @SuppressLint("Range")
    fun switchCollect(videoId: Int) {
        //Toast.makeText(context,"即将进行查询",Toast.LENGTH_SHORT).show()
        val results = modelDB.query(
            "videoInfo",
            null,
            "id = $videoId",
            null,
            null,
            null,
            null,
            null)
        results.moveToFirst()
        //Toast.makeText(context,"即将解析表格行数",Toast.LENGTH_SHORT).show()
        val videoIsLiked = results.getInt(results.getColumnIndex("videoIsCollect"))
        results.close()
        val values = ContentValues()
        //下面这句话那个三层嵌套函数意思是，把数字转成bool再取反，再转成数字。我懒得写lambda了
        values.put("videoIsCollect", toInt(!toBool(videoIsLiked)))
        //Toast.makeText(context,"即将进行改表",Toast.LENGTH_SHORT).show()
        modelDB.update(
            "videoInfo",
            values,
            "id = $videoId",
            null)

    }

    fun getCollectedModelVideo(): List<ModelVideoInfo> {

//        Toast.makeText(context,"getCollectedModelVideo",Toast.LENGTH_SHORT).show()
        return listOf(ModelVideoInfo(1), ModelVideoInfo(2), ModelVideoInfo(3),)


    }
    @SuppressLint("Range", "Recycle")
    class ModelVideoInfo(id1:Int) {
//        init{
////            Toast.makeText(context,catheIsInited.toString(),Toast.LENGTH_SHORT).show()
////            while (!catheIsInited){
////                sleep(1)
////            }
//
//        }
        private val idNow =id1-1
        val videoId = cathe[idNow].videoId
        var videoUri:String =  cathe[idNow].videoUri
        var videoName:String = cathe[idNow].videoName
        var videoIntroduce:String =  cathe[idNow].videoIntroduce
        var videoPicUri:String = cathe[idNow].videoPicUri

        var videoLike:Int = cathe[idNow].videoLike
        var videoUploaderId:Int = cathe[idNow].videoUploaderId
        var videoCollect:Int = cathe[idNow].videoCollect
        var videoUpdateTime:Int = cathe[idNow].videoUpdateTime
        var videoBelongCityId:Int = cathe[idNow].videoBelongCityId

        var videoIsLiked:Boolean = cathe[idNow].videoIsLiked
        var videoIsCollect:Boolean = cathe[idNow].videoIsCollect

        init{
//        videoUri = "android.resource://${context.packageName}/${R.raw.video2}"
//        val results = ModelVideoHelper.modelDB.query(
//            "videoInfo",
//            null,
//            "id = $id",
//            null,
//            null,
//            null,
//            null,
//            null)
//        results.moveToFirst()
//            Toast.makeText(context,results.count.toString(),Toast.LENGTH_SHORT).show()
//        videoUri = results.getString(results.getColumnIndex("videoUri"))
//        Toast.makeText(context,videoUri,Toast.LENGTH_SHORT).show()
//            videoName = results.getString(results.getColumnIndex("videoName"))
//            videoIntroduce = results.getString(results.getColumnIndex("videoIntroduce"))
//            videoPicUri = results.getString(results.getColumnIndex("videoPicUri"))
//
//            videoLike = results.getInt(results.getColumnIndex("videoLike"))
//            videoUploaderId = results.getInt(results.getColumnIndex("videoUploaderId"))
//            videoCollect = results.getInt(results.getColumnIndex("videoCollect"))
//            videoUpdateTime = results.getInt(results.getColumnIndex("videoUpdateTime"))
//            videoBelongCityId = results.getInt(results.getColumnIndex("videoBelongCityId"))
//            videoIsLiked = toBool(results.getInt(results.getColumnIndex("videoIsLiked")))
//            videoIsCollect = toBool(results.getInt(results.getColumnIndex("videoIsCollect")))
//        results.close()
//        sleep(200)



        }

    }
    @SuppressLint("Range", "Recycle")
    class ModelVideoCathe(
        val videoId:Int = 1,
        var videoUri:String = "android.resource://${context.packageName}/${R.raw.video1}",
        var videoName:String = "未命名视频",
        var videoIntroduce:String = "未命名视频",
        var videoPicUri:String = "android.resource://${context.packageName}/${R.raw.defaultpic}",

        var videoLike:Int = 0,
        var videoUploaderId:Int = 1,
        var videoCollect:Int = 0,
        var videoUpdateTime:Int = 10000000,
        var videoBelongCityId:Int = 1,
        var videoIsLiked:Boolean = false,
        var videoIsCollect:Boolean = false
    ) {





    }

}



