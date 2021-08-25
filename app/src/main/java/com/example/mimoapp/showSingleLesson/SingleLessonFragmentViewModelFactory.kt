package com.example.mimoapp.showSingleLesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mimoapp.repository.MimoLessonsRepository
import javax.inject.Inject

class SingleLessonFragmentViewModelFactory @Inject constructor(
    private val repository: MimoLessonsRepository
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleLessonFragmentViewModel(repository) as T
    }

}
