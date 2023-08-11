package com.example.animalia.screens.lessonOverview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animalia.R
import com.example.animalia.databinding.FragmentLessonOverviewBinding
import com.example.animalia.databinding.LessonOverviewRowItemBinding
import com.example.animalia.domain.Lesson
import com.google.android.material.chip.Chip

class LessonOverviewFragment : Fragment() {
    private lateinit var binding : FragmentLessonOverviewBinding
    private val viewModel: LessonOverviewViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, LessonOverviewViewModelFactory(activity.application)).get(LessonOverviewViewModel::class.java)
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

        addChips(listOf("gedaan", "nieuw", "alles"))

        binding.lessonOverview.adapter = adapter

        viewModel.lessons.observe(viewLifecycleOwner) {
            adapter.submitList(it.asList())
        }

        return binding.root
    }

    private fun addChips(chips: List<String>) {
        val chipGroup = binding.chipGroup
        val inflater = LayoutInflater.from(chipGroup.context)
        chips.forEach{
            val chip = inflater.inflate(R.layout.chip_filter, chipGroup, false) as Chip
            chip.text = it
            chip.tag = it
            chip.setOnCheckedChangeListener{
                button, isChecked ->
                viewModel.filterChip(button.tag as String, isChecked)
            }
            chipGroup.addView(chip)
        }
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
