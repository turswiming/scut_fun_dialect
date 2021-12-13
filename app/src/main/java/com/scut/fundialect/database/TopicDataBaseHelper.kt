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

open class TopicDataBaseHelper(val context: Context, name:String, version:Int):
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
    fun toUriStr(image:Int):String{
        return "android.resource://com.scut.fundialect/$image"
        //default "${toUriStr(com.scut.fundialect.R.raw.defaultpic)}"

    }
    //定义包名变量
    val packageName = "com.scut.fundialect"
    val DatePattern = "yyyy-MM-dd HH:mm:ss.SSS"
    fun initVideoDatabase(db: SQLiteDatabase?){
        repeat(5){
            val value1 = ContentValues().apply {
                put("videoUri","android.resource://${context.packageName}/${R.raw.video1}")
                put("videoName", "所以，冬天到底是北方冷还是南方冷？")
                put("videoLike", 342)
                put("videoIsLiked", 0)
                put("videoCollect", 134)
                put("videoIsCollect", 0)
                put("videoPicUri","${toUriStr(R.drawable.p1)}")
                put("videoIntroduce", "冬天渐渐来啦~又到了一年一度南北冷度比拼时间！南方人体会不到北方下雪下冰雹的寒冷，北方人也理解不了不到零下的温度怎么让人手脚冰冷的~来这里，说说你的冬日感受吧！")
                //SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                put("videoUpdateTime", Date().time -300000000)
                put("videoBelongCityId", 1)
            }
            db?.insert("videoInfo",null,value1)
            val value2 = ContentValues().apply {
                put("videoUri","android.resource://${context.packageName}/${R.raw.video2}")
                put("videoName", "山东人的倒装句是真文学瑰宝~")
                put("videoLike", 3420)
                put("videoIsLiked", 1)
                put("videoCollect", 1304)
                put("videoPicUri","${toUriStr(R.raw.p2)}")

                put("videoIsCollect", 0)
                put("videoIntroduce", "最近，#倒装句突然有了灵魂#的话题常常挂在热搜，有9000多万人阅读并参与讨论。倒装句啊，果不其然！让我们来感受一下，倒装句的精辟之处嗷！")
                put("videoUpdateTime", Date().time -600000000)
                put("videoBelongCityId", 1)

            }
            db?.insert("videoInfo",null,value2)
            val value3 = ContentValues().apply {
                put("videoUri","android.resource://${context.packageName}/${R.raw.video3}")

                put("videoName", "粽子吃甜的还是咸的？")
                put("videoLike", 1342)
                put("videoIsLiked", 1)
                put("videoCollect", 134)
                put("videoPicUri","${toUriStr(com.scut.fundialect.R.drawable.p3)}")

                put("videoIsCollect", 0)
                put("videoIntroduce", "每到端午节，各种各样的粽子争相上市，一年一度的粽子大战，也正式拉开帷幕。天下粽子，大致可分为\"京、浙、川、闽、粤\"五大流派，可盐可甜，让人一见“粽”情，欲罢不能。你喜欢那种口味？")
                put("videoUpdateTime", Date().time -60000000)
                put("videoBelongCityId", 1)

            }


        }


    }
    fun initCommentInfoDatabase(db: SQLiteDatabase?){
        val l1 = listOf<String>("你在南方的艳阳里大雪纷飞，我在北方的寒夜里四季如春，因为我有暖气。","南方人：“我这儿只有五度”，指，室内室外都只这个度数。",
            "北方的冷，官方认证！","暖气是个好东西，但我们这儿没有~","属于是传的多厚都会被冻到的程度。")
        l1.forEach {
            val value1 = ContentValues().apply {
                put("parentId", 1)
                put("commenterId", 2)
                put("comment", it)
                put("commentTime", Date().time -100000*(0..100).random())
                put("isLiked", (0..1).random())
                put("numberLiked", (0..1000).random())


            }
            db?.insert("commentInfo",null,value1)
        }
        val l2 = listOf<String>("“我没说吧现在”！","“真不是这样啊我们山东人”",
            "东北人前来挑战！不稀得说你我都","哈哈哈哈哈山东人致力于文学研究属于是","其实听起来还挺萌的如果习惯了的话")
        l2.forEach {
            val value1 = ContentValues().apply {
                put("parentId", 1)
                put("commenterId", 2)
                put("comment", it)
                put("commentTime", Date().time -100000*(0..100).random())
                put("isLiked", (0..1).random())
                put("numberLiked", (0..1000).random())


            }
            db?.insert("commentInfo",null,value1)
        }
        val l3 = listOf<String>("不会有人不喜欢吃红枣味的吧！","咸肉棕yyds！太好吃了！我馋了~",
            "孩子才做选择，成年人我都要！","哈哈哈哈哈接地气就行 能买着什么味儿就吃什么味儿！","想念妈妈包的豆沙粽了55555555")
        l3.forEach {
            val value1 = ContentValues().apply {
                put("parentId", 1)
                put("commenterId", 2)
                put("comment", it)
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