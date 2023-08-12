package com.example.animalia.utilities

import android.content.Context
import android.content.SharedPreferences

/**
 * Class used to store user variables globally
 * @param context The application context
 */
class SharedPreferences(context: Context) {

    private val EMAIL = "EMAIL"
    private val FIRSTNAME = "FIRSTNAME"
    private val CURRENT_LESSON = "CURENT_LESSON"
    private val CURRENT_QUESTION = "CURRENT_QUESTION"
    private val LOGGEDIN = "LOGGEDIN"

    private val preferences: SharedPreferences = context.getSharedPreferences(EMAIL, Context.MODE_PRIVATE)

    var emailCurrentUser: String?
        get() = preferences.getString(EMAIL, "")
        set(value) = preferences.edit().putString(EMAIL, value).apply()

    var firstName: String?
        get() = preferences.getString(FIRSTNAME, "")
        set(value) = preferences.edit().putString(FIRSTNAME, value).apply()

    var currentLesson: Int
        get() = preferences.getInt(CURRENT_LESSON, 0)
        set(value) = preferences.edit().putInt(CURRENT_LESSON, value).apply()

    var currentQuestion: Int
        get() = preferences.getInt(CURRENT_QUESTION, 0)
        set(value) = preferences.edit().putInt(CURRENT_QUESTION, value).apply()

    var loggedIn: String?
        get() = preferences.getString(LOGGEDIN, "")
        set(value) = preferences.edit().putString(LOGGEDIN, value).apply()

}