package com.scut.fundialect.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //关掉标题栏
        supportActionBar?.hide()
        //zxt到此一游
    }




}

