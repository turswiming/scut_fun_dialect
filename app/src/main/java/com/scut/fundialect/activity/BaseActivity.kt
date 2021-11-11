package com.scut.fundialect.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关掉标题栏
        context = getApplicationContext()
        supportActionBar?.hide()

    }

    /**
     * 获取全局上下文 */
    lateinit  var context:Context
    open fun getThisContext(): Context {
        return context
    }


}

