package com.dicoding.habitapp.ui.list

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.dicoding.habitapp.R


//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed

@RunWith(AndroidJUnit4::class)
class HabitListActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(HabitListActivity::class.java)

    @Test
    fun testFabOpensAddTaskActivity() {
        Espresso.onView(withId(R.id.fab)).perform(ViewActions.click())

        Espresso.onView(withId(R.id.sp_priority_level))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
