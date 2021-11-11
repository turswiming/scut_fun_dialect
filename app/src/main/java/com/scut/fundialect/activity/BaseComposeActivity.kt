package com.scut.fundialect.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity


open class BaseComposeActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关掉标题栏



    }
    /**
     * 获取全局上下文 */
    lateinit  var context: Context
    open fun getThisContext(): Context {
        return context
    }
}