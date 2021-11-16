package com.scut.fundialect.activity.culture

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.scut.fundialect.activity.BaseComposeActivity
import com.scut.fundialect.activity.culture.ui.theme.FunDialectTheme

class CultureActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            FunDialectTheme {
                /** A surface container using the 'background' color from the theme*/
                Surface(color = MaterialTheme.colors.background) {


                }
            }
        }

    }


}

