package com.scut.fundialect.activity.publicCompose.util

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<T>.set(block: T.() -> T) {
    this.value = this.value.block()
}