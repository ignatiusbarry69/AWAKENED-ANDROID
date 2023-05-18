package com.example.storyapp.ui.signin

import android.util.Log
import com.example.storyapp.R
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SigninActivityTest {
    private val email = "tes123@gmail.com"
    private val password = "llllllll"

    @Before
    fun setup() {
        ActivityScenario.launch(SigninActivity::class.java)
    }

    @Test
    fun setupAction() {
        Log.d("UITest", "Starting setupAction")

        Thread.sleep(1000)

        onView(withId(R.id.emailEditText)).check(matches(isDisplayed()))
        Log.d("UITest", "EmailEditText is displayed")

        onView(withId(R.id.emailEditText)).perform(typeText(email), closeSoftKeyboard())
        Log.d("UITest", "EmailEditText typed with $email")

        onView(withId(R.id.passwordEditText)).check(matches(isDisplayed()))
        Log.d("UITest", "PasswordEditText is displayed")

        onView(withId(R.id.passwordEditText)).perform(typeText(password), closeSoftKeyboard())
        Log.d("UITest", "PasswordEditText typed with $password")

        Thread.sleep(1000)

        onView(withId(R.id.loginButton)).check(matches(isDisplayed()))
        Log.d("UITest", "LoginButton is displayed")

        onView(withId(R.id.loginButton)).perform(click())
        Log.d("UITest", "LoginButton clicked")
    }
}
