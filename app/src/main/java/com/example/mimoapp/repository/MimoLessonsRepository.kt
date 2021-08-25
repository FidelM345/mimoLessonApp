package com.example.mimoapp.repository

import com.example.mimoapp.api.MimoLessonsAPI
import com.example.mimoapp.api.model.MimoLessons
import com.example.mimoapp.roomDB.LessonCompletedModel
import com.example.mimoapp.roomDB.LessonsDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException


import javax.inject.Inject

class MimoLessonsRepository @Inject constructor(
    private val mimoLessonsAPI: MimoLessonsAPI,
    private val provideAppDatabase: LessonsDatabase

) {

    suspend fun fetchAllMimoLessons(): Flow<Result<MimoLessons>> {

        return flow {
            emit(Result.success(mimoLessonsAPI.fetchAllMimoLessons()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

    suspend fun retrieveSavedLessonDetails(lessonID:Int)=
        provideAppDatabase.getLessonDAO().retrieveSavedLessonDetails(lessonID)

    suspend fun insertCompletedLesson(lessonCompleted: LessonCompletedModel) =
        provideAppDatabase.getLessonDAO().insertCompletedLesson(lessonCompleted)


}