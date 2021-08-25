package com.example.mimoapp.showSingleLesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mimoapp.repository.MimoLessonsRepository
import com.example.mimoapp.roomDB.LessonCompletedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SingleLessonFragmentViewModel(private val repository: MimoLessonsRepository) : ViewModel() {

    fun insertCompletedLesson(lessonCompleted: LessonCompletedModel) = viewModelScope.launch(Dispatchers.IO) {

        repository.insertCompletedLesson(lessonCompleted)
    }


    fun retrieveSavedLessonDetails(lessonID:Int)=liveData<LessonCompletedModel>{
        repository.retrieveSavedLessonDetails(lessonID)?.asLiveData()?.let {
            emitSource(
                it
            )
        }
    }

}