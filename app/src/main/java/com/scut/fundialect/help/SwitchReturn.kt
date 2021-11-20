package com.scut.fundialect.help

/**
 *
 * 这是一个简单的用于切换状态的代码。
 * 根据需要，当最后一个bool值为真的时候，返回第一个变量，Bool值为假的时候，返回另外一个。
 *
 *
 * */
fun <T> switch( returnIfTrue: T,returnIfFalse: T,boolean: Boolean): T {

    if(boolean){
        return  returnIfTrue
    }

    return returnIfFalse
}
