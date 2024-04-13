package com.dicoding.todoapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.todoapp.R

//TODO 16 : Write UI test to validate when user tap Add Task (+), the AddTaskActivity displayed
@RunWith(AndroidJUnit4::class)
class TaskActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(TaskActivity::class.java)

    @Test
    fun testFabOpensAddTaskActivity() {
        // clicking fab (+)
        onView(withId(R.id.fab)).perform(click())

        // check AddTaskActivity displayed by using isDisplayed on add_tv_due_date
        onView(withId(R.id.add_tv_due_date)).check(matches(isDisplayed()))
    }
}