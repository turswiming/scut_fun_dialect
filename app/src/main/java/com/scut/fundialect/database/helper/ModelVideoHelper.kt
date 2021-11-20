package com.scut.fundialect.database.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.scut.fundialect.MyApplication
import com.scut.fundialect.database.ModelVideoDataBaseHelper

object ModelVideoHelper {
    private  var modelDB: SQLiteDatabase

    init {
        val modelVideoDataBaseHelper = ModelVideoDataBaseHelper(
            MyApplication.context,
            "Learn.db",
            1
        )
        modelDB =modelVideoDataBaseHelper.writableDatabase
    }
    fun getCommitNumber(videoId: Int):Int {
        return 666
    }
    var lastShowedVideo = 0
    /**
     * 自动获取下一个视频。
     * 视频内容不会重样
     * 所有查询语句都封装好了，你一点都看不着。
     * **/
    fun getNextVideo():VideoInfo{
        try {
            return VideoInfo(++lastShowedVideo)

        } catch (e: Exception) {
            lastShowedVideo=1
            return VideoInfo(1)
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
                commentInfoList+=CommentInfo(id)

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

    @SuppressLint("Range", "Recycle")
    class VideoInfo(id:Int) {
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
            val results = modelDB.query(
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

    @SuppressLint("Range")
    fun getCollectedVideo():MutableList<VideoInfo> {
        val results = modelDB.query(
            "videoInfo",
            null,
            "videoIsCollect = 1",
            null,
            null,
            null,
            null)
        Toast.makeText(MyApplication.context,"查表获取了${results.count}个元素", Toast.LENGTH_SHORT).show()
        var videoInfoList: MutableList<VideoInfo> = mutableListOf<VideoInfo>()


        if (results.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                var id = results.getInt(results.getColumnIndex("id"))
                videoInfoList.add(VideoInfo(id))

            } while (results.moveToNext())
        }
        results.close()


        return videoInfoList


    }
}