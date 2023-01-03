package br.com.mdr.test.extension

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText

fun String.isTextDisplayed(): ViewInteraction = onView(withText(this)).check(matches(isDisplayed()))
