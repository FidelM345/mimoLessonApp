package com.example.mimoapp.repository

import android.provider.SyncStateContract
import org.junit.Assert.*

import com.example.androidplaylist.utils.BaseunitTest
import com.example.mimoapp.api.MimoLessonsAPI
import com.example.mimoapp.roomDB.LessonDAO
import com.example.mimoapp.roomDB.LessonsDatabase
import com.example.mimoapp.util.Constants
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test
import org.mockito.Mockito.verify

class MimoLessonsRepositoryShould : BaseunitTest() {
    private val lessonsDatabase: LessonsDatabase = mock()
    private val mimoLesssonAPI:MimoLessonsAPI = mock()
    private val lessonDAO:LessonDAO= mock()
    @Test
    fun `should fetch all mimolessons from the remote api`() = runBlockingTest{
        val mimoLessonsRepository =MimoLessonsRepository(mimoLesssonAPI,lessonsDatabase)

        //the single method asks our Kotlinflow to emit the first result
        mimoLessonsRepository.fetchAllMimoLessons().single()

        verify(mimoLesssonAPI, times(1)).fetchAllMimoLessons()
    }

}