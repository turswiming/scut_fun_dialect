package com.scut.fundialect.activity.video.util

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.set(block: T.() -> T) {
    this.value = this.value.block()
}