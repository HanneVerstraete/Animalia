package com.example.animalia.screens.truefalse

import android.os.Handler

class QuestionTimer {
    var secondsCount = 0
    private var handler = Handler()
    private lateinit var runnable: Runnable


    fun startTimer() {
        runnable = Runnable {
            secondsCount++
            handler.postDelayed(runnable, 1000)
        }

        handler.postDelayed(runnable, 1000)
    }

    fun stopTimer() {
        handler.removeCallbacks(runnable)
    }
}