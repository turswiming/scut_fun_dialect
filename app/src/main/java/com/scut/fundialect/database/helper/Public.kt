package com.scut.fundialect.database.helper

import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
import com.scut.fundialect.MyApplication.Companion.context

object Public {
    private var  VideodataInitNum =0
    set(value) {
        Looper.prepare();
//        Toast.makeText(context,"数据库已完成一个",Toast.LENGTH_SHORT).show()
        Looper.loop();
        if(value>=3&&CityDataInit!=1){
            Looper.prepare();
            Toast.makeText(context,"数据库加载已完成",Toast.LENGTH_SHORT).show()
            Looper.loop();
        }
        field = value
    }
    var CityDataInit =0
        set(value) {
            Looper.prepare();
//            Toast.makeText(context,"数据库已完成一个",Toast.LENGTH_SHORT).show()
            Looper.loop();

            if(VideodataInitNum>=2&&value!=1){
                Looper.prepare();
                Toast.makeText(context,"数据库加载已完成",Toast.LENGTH_SHORT).show()
                Looper.loop();

            }
            field = value
        }
    @Synchronized
    public fun isComplite():Boolean{
        return VideodataInitNum>=3&&CityDataInit!=0
    }
    fun finishDatainit(){
        VideodataInitNum+=1
    }
}