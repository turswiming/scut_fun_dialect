package com.scut.fundialect.help

import androidx.compose.foundation.lazy.LazyListState
import java.util.*

class MyTimerTask(var  lazyListState: LazyListState) : TimerTask() {
    override fun run() {
        //这里填上每次触发要执行的代码
        suspend {
            lazyListState.animateScrollToItem(
                lazyListState.firstVisibleItemIndex+1,
                -150,

            )
        }

    }
}