package com.example.animalia.screens.lessonOverview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animalia.R
import com.example.animalia.database.lessons.Lesson
import com.example.animalia.database.lessons.LessonDatabase
import com.example.animalia.databinding.FragmentLessonOverviewBinding
import com.example.animalia.databinding.LessonOverviewRowItemBinding

class LessonOverviewFragment : Fragment() {
    private lateinit var binding: FragmentLessonOverviewBinding
    private val viewModel: LessonOverviewViewModel by viewModels {
        val appContext = requireActivity().application
        val dataSource = LessonDatabase.getInstance(appContext).lessonDatabaseDao

        LessonOverviewViewModelFactory(dataSource)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_overview, container, false)

        val adapter = CustomAdapter(LessonListener {
            // TODO
            lessonId -> Toast.makeText(context, "$lessonId", Toast.LENGTH_SHORT).show()
        })

        binding.lessonOverview.adapter = adapter

        viewModel.lessons.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}

class ViewHolder(val binding: LessonOverviewRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val lessonText: TextView = binding.lessonText

    fun bind(clickListener: LessonListener, lesson: Lesson) {
        lessonText.text = lesson.title
        binding.lesson = lesson
        binding.clickListener = clickListener
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LessonOverviewRowItemBinding.inflate(layoutInflater, parent, false)
            return ViewHolder(binding)
        }
    }
}

class LessonListener(val clickListener: (lessonId: Long)->Unit) {
    fun onClick(lesson: Lesson) = clickListener(lesson.lessonId)
}

class CustomAdapter(private val clickListener: LessonListener) :
    ListAdapter<Lesson, ViewHolder>(object : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(
            oldLesson: Lesson, newLesson: Lesson
        ): Boolean {
            return oldLesson.lessonId === newLesson.lessonId
        }

        // TODO also check user?
        override fun areContentsTheSame(
            oldLesson: Lesson, newLesson: Lesson
        ): Boolean {
            return oldLesson == newLesson
        }

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val lesson = getItem(position)
        viewHolder.bind(clickListener, lesson)
    }
}
