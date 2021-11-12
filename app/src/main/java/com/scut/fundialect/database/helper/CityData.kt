package com.scut.fundialect.database.helper

import com.scut.fundialect.MyApplication.Companion.context
import com.scut.fundialect.database.CityDataBaseHelper

object CityData {
    init {
        val cityDataBaseHelper = CityDataBaseHelper(context,"city.db",1)
        val cityDB = cityDataBaseHelper.writableDatabase
    }
}