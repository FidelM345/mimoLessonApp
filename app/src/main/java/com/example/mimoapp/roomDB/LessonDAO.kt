package com.example.mimoapp.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCompletedLesson(lessonCompleted: LessonCompletedModel):Long


    @Query("SELECT * FROM lessons_completed_table where lessonID = :lessonID")
    fun retrieveSavedLessonDetails(lessonID:Int): Flow<LessonCompletedModel>?


}