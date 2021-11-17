package com.scut.fundialect.help


fun <T> switch( returnIfTrue: T,returnIfFalse: T,boolean: Boolean): T {

    if(boolean){
        return  returnIfTrue
    }

    return returnIfFalse
}
