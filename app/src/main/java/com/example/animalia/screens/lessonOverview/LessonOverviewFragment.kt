package com.example.animalia.screens.lessonOverview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.animalia.R
import com.example.animalia.database.lessons.Lesson
import com.example.animalia.database.lessons.LessonDatabase
import com.example.animalia.database.users.User
import com.example.animalia.databinding.FragmentLessonOverviewBinding

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

        val adapter = CustomAdapter()
        binding.lessonOverview.adapter = adapter

        viewModel.lessons.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val lessonText: TextView = view.findViewById(R.id.lessonText)
    val lessonImage: ImageView = view.findViewById(R.id.imageView)
}

// TODO no 2 classes in 1 file?
class CustomAdapter() :
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
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.lesson_overview_row_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // TODO get actual user
        val user = User(301, 1)
        val lesson = getItem(position)

        viewHolder.lessonText.text = lesson.title
        val drawableResource: Int = if (user.lastLessonIndex >= lesson.index) {
            R.drawable.star_gold
        } else {
            R.drawable.star_empty
        }
        viewHolder.lessonImage.setImageResource(drawableResource)
    }
}
