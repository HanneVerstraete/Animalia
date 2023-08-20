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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animalia.R
import com.example.animalia.databinding.FragmentLessonOverviewBinding
import com.example.animalia.databinding.LessonOverviewRowItemBinding
import com.example.animalia.domain.Lesson
import com.example.animalia.sharedPreferences
import com.google.android.material.chip.Chip

class LessonOverviewFragment : Fragment() {
    private lateinit var binding: FragmentLessonOverviewBinding
    private val viewModel: LessonOverviewViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, LessonOverviewViewModelFactory(activity.application)).get(
            LessonOverviewViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_lesson_overview, container, false)

        val adapter = CustomAdapter(LessonListener { lessonIndex ->
            if (lessonIndex <= sharedPreferences.currentLesson) {
                view?.findNavController()?.navigate(
                    LessonOverviewFragmentDirections.actionLessonOverviewFragmentToLessonFragment(
                        lessonIndex
                    )
                )
            } else {
                Toast.makeText(context, getString(R.string.warning_lesson_not_enabled), Toast.LENGTH_LONG).show()
            }
        })

        addChips(listOf("gedaan", "te doen", "alles"))

        binding.lessonOverview.adapter = adapter

        viewModel.lessons.observe(viewLifecycleOwner) {
            adapter.submitList(it.asList())
        }

        return binding.root
    }

    private fun addChips(chips: List<String>) {
        val chipGroup = binding.chipGroup
        val inflater = LayoutInflater.from(chipGroup.context)
        chips.forEach {
            val chip = inflater.inflate(R.layout.chip_filter, chipGroup, false) as Chip
            chip.text = it
            chip.tag = it
            chip.setOnCheckedChangeListener { button, isChecked ->
                viewModel.filterChip(button.tag as String, isChecked)
            }
            chipGroup.addView(chip)
        }
    }
}

class ViewHolder(val binding: LessonOverviewRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private val lessonTextEnabled: TextView = binding.lessonTextEnabled
    private val lessonTextDisabled: TextView = binding.lessonTextDisabled

    fun bind(clickListener: LessonListener, lesson: Lesson) {
        if (lesson.index > sharedPreferences.currentLesson) {
            lessonTextEnabled.visibility = View.GONE
            lessonTextDisabled.visibility = View.VISIBLE
            lessonTextDisabled.text = lesson.title
        } else {
            lessonTextDisabled.visibility = View.GONE
            lessonTextEnabled.visibility = View.VISIBLE
            lessonTextEnabled.text = lesson.title
        }
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

class LessonListener(val clickListener: (index: Int) -> Unit) {
    fun onClick(lesson: Lesson) = clickListener(lesson.index)
}

class CustomAdapter(private val clickListener: LessonListener) :
    ListAdapter<Lesson, ViewHolder>(object : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(
            oldLesson: Lesson, newLesson: Lesson
        ): Boolean {
            return oldLesson.lessonId === newLesson.lessonId
        }

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
