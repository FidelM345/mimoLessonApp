package com.example.mimoapp.roomDB

import androidx.room.TypeConverter
import com.example.mimoapp.util.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class TimestampConverter {

    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}
