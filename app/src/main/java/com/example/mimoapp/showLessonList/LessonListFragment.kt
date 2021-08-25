package com.example.mimoapp.showLessonList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mimoapp.R
import com.example.mimoapp.api.model.Lesson
import com.example.mimoapp.databinding.FragmentLessonListBinding
import com.example.mimoapp.util.showMessageToUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LessonListFragment : Fragment(R.layout.fragment_lesson_list) {
private lateinit var lessonsList: MutableList<Lesson>
private lateinit var binding: FragmentLessonListBinding
    @Inject
    lateinit var viewModelFactory: LessonListViewModelFactory

    val viewModel: LessonListFragmentViewModel by activityViewModels {
        viewModelFactory
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding the layout file to access its views
        binding = FragmentLessonListBinding.bind(view)

        lessonsList = mutableListOf()


        retrieveAndShowMimoLessonsOnRecyclerview()

        controlProgressBarVisibility()

    }

    private fun controlProgressBarVisibility() {
        viewModel.isProgressBarVisible.observe(viewLifecycleOwner, {

            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }

        })
    }

    private fun retrieveAndShowMimoLessonsOnRecyclerview() {
        viewModel.mimoLesonsList.observe(viewLifecycleOwner, { it ->

            it.onSuccess { mimoLesson ->

                lessonsList.add(mimoLesson.lessons[0])

                setupRecyclerView(binding.lessonlistRecyclerView, mimoLesson.lessons)
            }
            it.onFailure {
                showMessageToUser(requireContext(), it.localizedMessage)
            }

        })
    }


    private fun setupRecyclerView(view: View?, lessonList: List<Lesson>) {
        //casting the view to a recycler view

        var lessonListAdapter = LessonListAdapter {

            val  action: NavDirections = LessonListFragmentDirections.actionLessonListFragmentToSingleLessonFragment(it)
            findNavController().navigate(action)


        }
        lessonListAdapter.submitList(lessonList)

        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = lessonListAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }

}