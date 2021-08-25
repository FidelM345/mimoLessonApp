package com.example.mimoapp.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.mimoapp.api.MimoLessonsAPI
import com.example.mimoapp.roomDB.LessonCompletedModel
import com.example.mimoapp.roomDB.LessonsDatabase
import com.example.mimoapp.util.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import java.io.IOException

class MimoLessonsRepositoryTest {

    private lateinit var authDatabase: LessonsDatabase
    private lateinit var authenticationRepo: MimoLessonsRepository
    private lateinit var completedlessondetails: LessonCompletedModel
    private var mimoLessonsAPI =mock(MimoLessonsAPI::class.java)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        authDatabase = Room.inMemoryDatabaseBuilder(context, LessonsDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()

        authenticationRepo = MimoLessonsRepository(mimoLessonsAPI, authDatabase)
        completedlessondetails = LessonCompletedModel(
            Constants.TEST_LESSON_ID,
            Constants.TEST_LESSON_START_TIME, Constants.TEST_LESSON_END_TIME
        )
    }

    @After
    fun tearDown() {
        authDatabase.close()
    }

    @Test
    @Throws(IOException::class)
    fun shouldInsertAndRetrieveCompletedLessonDetailsFromDB() = runBlocking {
        authenticationRepo.insertCompletedLesson(completedlessondetails)
        val lessonDetailsFromDB = authenticationRepo.retrieveSavedLessonDetails(
           Constants.TEST_LESSON_ID
        )?.first()
        assertEquals(completedlessondetails, lessonDetailsFromDB)
    }
}