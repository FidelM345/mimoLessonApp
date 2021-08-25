package com.example.mimoapp.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "lessons_completed_table")
@TypeConverters(TimestampConverter::class)
data class LessonCompletedModel (
    @PrimaryKey
    val lessonID: Int,

    val startTime: Date,

    val endTime:Date
        )