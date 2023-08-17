package com.example.animalia

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest{
    @Test
    fun test_visibility_home_fragment() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_home_buttons() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_lesson_button)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.start_truefalse_button)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_nav_back() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_lesson_button)).perform(click())
        onView(withId(R.id.lesson_fragment)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()))

        onView(withId(R.id.start_truefalse_button)).perform(click())
        onView(withId(R.id.truefalse_fragment)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun test_nav_lesson_fragment() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_lesson_button)).perform(click())
        onView(withId(R.id.lesson_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun test_nav_lesson_fragment_buttons() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_lesson_button)).perform(click())

        onView(withId(R.id.nextlesson_button)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.home_button)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.previouslesson_button)).check(matches(withEffectiveVisibility(Visibility.INVISIBLE)))
    }

    @Test
    fun test_nav_lesson_home_button_click() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_lesson_button)).perform(click())
        onView(withId(R.id.lesson_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.home_button)).perform(click())
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun test_nav_truefalse_fragment() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_truefalse_button)).perform(click())
        onView(withId(R.id.truefalse_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun test_nav_truefalse_end_fragment() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_truefalse_button)).perform(click())
        onView(withId(R.id.truefalse_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.true_button)).perform(click())
        onView(withId(R.id.true_button)).perform(click())
        onView(withId(R.id.true_button)).perform(click())
        onView(withId(R.id.truefalse_end_fragment)).check(matches(isDisplayed()))
    }

    fun test_nav_truefalse_end_quit_click() {
        ActivityScenario.launch(LoginActivity::class.java)

        onView(withId(R.id.start_truefalse_button)).perform(click())
        onView(withId(R.id.truefalse_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.true_button)).perform(click())
        onView(withId(R.id.true_button)).perform(click())
        onView(withId(R.id.true_button)).perform(click())
        onView(withId(R.id.truefalse_end_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.continue_button)).perform(click())
        onView(withId(R.id.home_fragment)).check(matches(isDisplayed()))
    }
}