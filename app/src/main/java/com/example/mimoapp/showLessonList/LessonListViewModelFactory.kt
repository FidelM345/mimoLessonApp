package com.example.mimoapp.showLessonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mimoapp.repository.MimoLessonsRepository
import javax.inject.Inject


class LessonListViewModelFactory @Inject constructor(
    private val repository: MimoLessonsRepository
) :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LessonListFragmentViewModel(repository) as T
    }

}

