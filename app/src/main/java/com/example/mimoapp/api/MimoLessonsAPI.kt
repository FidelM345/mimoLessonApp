package com.example.mimoapp.api

import com.example.mimoapp.api.model.MimoLessons
import retrofit2.http.GET

interface MimoLessonsAPI {

    @GET("lessons")
    suspend fun fetchAllMimoLessons():MimoLessons
}