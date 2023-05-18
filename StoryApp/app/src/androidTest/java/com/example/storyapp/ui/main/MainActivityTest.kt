package com.example.storyapp.ui.main

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.example.storyapp.R
import org.junit.Rule

import org.junit.Test

class MainActivityTest {

    @get:Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActionMenuClick() {
        // Open the overflow menu programmatically

        Thread.sleep(1000)
        // Click the action menu item
        openActionBarOverflowOrOptionsMenu(getInstrumentation().targetContext)

        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.id.logout)).perform(ViewActions.click())

        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(R.string.logout)).perform(ViewActions.click())
    }
}
