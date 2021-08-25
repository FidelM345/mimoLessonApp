package com.example.mimoapp.util

import com.google.gson.GsonBuilder

import com.google.gson.Gson
import java.sql.Time


object Constants {
    const val BASE_URL = "https://mimochallenge.azurewebsites.net/api/"
    const val TEST_LESSON_ID =5
    val TEST_LESSON_START_TIME=Time(System.currentTimeMillis())
    val TEST_LESSON_END_TIME=Time(System.currentTimeMillis())

}