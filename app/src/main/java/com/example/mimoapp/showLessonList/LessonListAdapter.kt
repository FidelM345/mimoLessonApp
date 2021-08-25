package com.example.mimoapp.showLessonList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mimoapp.R
import com.example.mimoapp.showLessonList.LessonListAdapter.*
import com.example.mimoapp.api.model.Lesson

class LessonListAdapter(private val onClick: (Lesson) -> Unit) :
    ListAdapter<Lesson, LessonsViewHolder>(LessonDiffCallback) {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    val currentLesson: Lesson? = null

    class LessonsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val lessonTitleTxt: TextView = view.findViewById(R.id.lesson_title_txt)

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): LessonsViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.lesson_list_item, viewGroup, false)

        return LessonsViewHolder(view)
    }

    override fun onBindViewHolder(lessonsViewHolder: LessonsViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        var lesson: Lesson = getItem(position)
        lessonsViewHolder.apply {
            lessonTitleTxt.text = "The Lesson ID: ${lesson.id}"

            itemView.setOnClickListener {
                onClick(lesson)
            }

        }


    }


    object LessonDiffCallback : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.id == newItem.id
        }
    }
}