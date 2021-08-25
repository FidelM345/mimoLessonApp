package com.example.mimoapp.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [LessonCompletedModel::class],
    version =2
)
abstract class LessonsDatabase :RoomDatabase(){
    abstract fun getLessonDAO(): LessonDAO
}