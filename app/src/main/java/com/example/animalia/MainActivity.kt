package com.example.animalia

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var lessonText: TextView
    lateinit var nextlessonButton: Button
    lateinit var quitlessonButton: Button
    lateinit var animalImage: ImageView

    var lessonNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // find elements
        lessonText = findViewById(R.id.lesson_textview)
        nextlessonButton = findViewById(R.id.nextlesson_button)
        quitlessonButton = findViewById(R.id.quitlesson_button)
        animalImage = findViewById(R.id.animal_image)

        // set onclick listeners
        nextlessonButton.setOnClickListener { nextLesson() }

        quitlessonButton.setOnClickListener { quitLesson() }
    }

    private fun nextLesson() {
        val lesson = AnimalsLesson.getNextLesson(lessonNumber)
        lessonText.text = lesson

        when (lessonNumber) {
            0 -> animalImage.setImageResource(R.drawable.dog)
            1 -> animalImage.setImageResource(R.drawable.lion)
            2 -> animalImage.setImageResource(R.drawable.duck)
            else -> animalImage.setImageResource(R.drawable.empty_vector)
        }

        lessonNumber += 1
    }

    private fun quitLesson() {
        // TODO
    }
}
