package br.com.mdr.test.extension

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

private fun performAction(id: Int, action: ViewAction) = Espresso.onView(ViewMatchers.withId(id)).perform(action)

fun Int.click(): ViewInteraction = performAction(this, ViewActions.click())

fun Int.hasText(text: String): ViewInteraction = Espresso.onView(ViewMatchers.withId(this))
    .check(ViewAssertions.matches(ViewMatchers.withText(text)))

