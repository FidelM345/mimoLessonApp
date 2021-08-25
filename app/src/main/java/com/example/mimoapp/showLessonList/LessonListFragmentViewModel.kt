package com.example.mimoapp.showLessonList



import androidx.lifecycle.*
import com.example.mimoapp.api.model.MimoLessons
import com.example.mimoapp.repository.MimoLessonsRepository
import kotlinx.coroutines.flow.onEach

class LessonListFragmentViewModel(private val mimoLessonsRepository: MimoLessonsRepository): ViewModel() {
    val isProgressBarVisible = MutableLiveData<Boolean>()


    val mimoLesonsList= liveData<Result<MimoLessons>> {
        isProgressBarVisible.postValue(true)
        //  Log.i("mato", "viewmodel playlist size = ${repository.getPlaylists().collect{it.getOrNull()!!.size}} ")
        emitSource(mimoLessonsRepository.fetchAllMimoLessons()
            //on each flow emitted we need to close the loader
            .onEach {
                isProgressBarVisible.postValue(false)
            }
            .asLiveData())
    }

}