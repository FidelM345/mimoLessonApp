package com.example.mimoapp.showLessonList

import org.junit.Assert.*

import com.example.androidplaylist.utils.BaseunitTest
import com.example.mimoapp.api.model.MimoLessons
import com.example.mimoapp.repository.MimoLessonsRepository
import com.example.mimoapp.roomDB.LessonsDatabase
import com.example.mimoapp.showSingleLesson.SingleLessonFragmentViewModel
import com.example.mimoapp.util.Constants
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class LessonListFragmentViewModelShould : BaseunitTest() {
    private val repository: MimoLessonsRepository = mock()//mocking the repo class
    private val mimoLessons: MimoLessons = mock()
    private val exception= RuntimeException("Something went wrong")



    @Test
    fun `get mimolessons list from the MimoLessonsRepository class`() = runBlockingTest {

        val lessonlistFragmentViewModel = LessonListFragmentViewModel(repository)

        //we use the getValueFOrTest() to force an emission by the livedata
        lessonlistFragmentViewModel.mimoLesonsList.getValueForTest()

        verify(repository, times(1)).fetchAllMimoLessons()

    }

    @Test
    fun `emit the same mimolessons as those emitted by the MimoLessonsRepository class`() =

        runBlockingTest {

            mockSuccessfulCase()

            val lessonlistFragmentViewModel = LessonListFragmentViewModel(repository)

            //we use the getValueFOrTest() to force an emission by the livedata
            val emittedMimoLessons = lessonlistFragmentViewModel.mimoLesonsList.getValueForTest()

            assertEquals(emittedMimoLessons, Result.success(mimoLessons))

        }

    @Test
    fun `emit error when it receives an error from the MimoRepository class`() = runBlockingTest {

        mockErrorCase()


        val lessonlistFragmentViewModel = LessonListFragmentViewModel(repository)

        assertEquals(exception, lessonlistFragmentViewModel.mimoLesonsList.getValueForTest()!!.exceptionOrNull())


    }


    @Test
    fun `close progressBar after playlist loads`() = runBlockingTest {
        mockSuccessfulCase()


        val lessonlistFragmentViewModel = LessonListFragmentViewModel(repository)

        lessonlistFragmentViewModel.isProgressBarVisible.captureValues {
            lessonlistFragmentViewModel.mimoLesonsList.getValueForTest()

            assertEquals(false, values.last())
        }


    }


    @Test
    fun `show progressBar while loading`() = runBlockingTest {
        mockSuccessfulCase()

        val lessonlistFragmentViewModel = LessonListFragmentViewModel(repository)

        //the capture command is also from the liveData util file
        lessonlistFragmentViewModel.mimoLesonsList.captureValues {

            //we force the live data to emit results using the getValueForTest()
            lessonlistFragmentViewModel.mimoLesonsList.getValueForTest()
            //values array is from the LiveData Util file
            //we the assert that the first value emitted is true
            assertEquals(true, values[0]!!.isSuccess)
        }

    }

    private suspend fun mockSuccessfulCase() {
        whenever(repository.fetchAllMimoLessons()).thenReturn(
            flow {
                emit(Result.success(mimoLessons))
            }
        )
    }

    private suspend fun mockErrorCase() {
        whenever(repository.fetchAllMimoLessons()).thenReturn(
            flow {
                emit(Result.failure<MimoLessons>(exception))
            }
        )
    }



}