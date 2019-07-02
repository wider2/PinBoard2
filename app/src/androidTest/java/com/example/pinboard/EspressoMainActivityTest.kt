package com.example.pinboard

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.example.pinboard.CustomAssertions.Companion.hasItemCount
import com.example.pinboard.CustomMatchers.Companion.withItemCount
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun countItems() {
        onView(withId(R.id.pinsRecyclerView))
            .check(matches(withItemCount(10)))
    }

    @Test
    fun countItemsWithViewAssertion() {
        onView(withId(R.id.pinsRecyclerView))
            .check(hasItemCount(10))
    }

    @Test
    fun goThrough() {
        onView(ViewMatchers.withId(R.id.pinsRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.imageViewCancel))
            .check(matches(isAssignableFrom(ImageView::class.java)))

        Thread.sleep(1000);

        onView(withId(R.id.imageViewCancel)).perform(click()).check(matches(isDisplayed()));

        onView(withId(R.id.imageViewReload)).perform(click()).check(matches(isDisplayed()));

        //onView(withId(R.id.imageViewPhoto)).perform(click()).check(matches(isDisplayed()));
    }

}