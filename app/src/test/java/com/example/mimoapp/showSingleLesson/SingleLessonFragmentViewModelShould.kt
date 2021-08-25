package com.example.mimoapp.showSingleLesson

import org.junit.Assert.*

import com.example.androidplaylist.utils.BaseunitTest
import com.example.mimoapp.repository.MimoLessonsRepository
import com.example.mimoapp.roomDB.LessonCompletedModel
import com.example.mimoapp.util.Constants
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class SingleLessonFragmentViewModelShould : BaseunitTest() {
    private val repository: MimoLessonsRepository = mock()//mocking the repo class
    private val lessonDetails: LessonCompletedModel = mock()

    /*   private val mimolessons
       private val expected=Result.success(playlist)//used to mock a successful result of playlist. So we use the successful results builder.
       private val exception= RuntimeException("Something went wrong")*/

    @Test
    fun `retrieve Saved Lessons Details from the MimoLessonsRepository class`() = runBlockingTest {

        val singleLessonFragmentViewModel = SingleLessonFragmentViewModel(repository)

        //we use the getValueFOrTest() to force an emission by the livedata
        singleLessonFragmentViewModel.retrieveSavedLessonDetails(Constants.TEST_LESSON_ID)
            .getValueForTest()

        verify(repository, times(1)).retrieveSavedLessonDetails(Constants.TEST_LESSON_ID)
    }

    @Test
    fun `receive the same lessonDetails as those emitted by the MimoLessonsRepository class`() =

        runBlockingTest {

            whenever(repository.retrieveSavedLessonDetails(Constants.TEST_LESSON_ID)).thenReturn(
                flow {
                    emit(lessonDetails)
                }
            )

            val singleLessonFragmentViewModel = SingleLessonFragmentViewModel(repository)

            //we use the getValueFOrTest() to force an emission by the livedata
            val receivedLessonDetails=singleLessonFragmentViewModel.retrieveSavedLessonDetails(Constants.TEST_LESSON_ID)
                .getValueForTest()

            assertEquals(receivedLessonDetails,lessonDetails)

        }

}