package com.kubaty.catfacts.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.kubaty.catfacts.R
import com.kubaty.catfacts.TestFactsApplication
import com.kubaty.catfacts.di.DaggerTestFactsComponent
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FactListFragmentTest {

    @Test
    fun test_areFactDetailsVisible() {
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestFactsApplication
        DaggerTestFactsComponent.builder().application(app).build().apply { inject(app) }
        val scenario = launchActivity<MainActivity>()
        scenario.onActivity {
            val recyclerView: RecyclerView = it.findViewById(R.id.rv_facts)
            assertNotEquals(0, recyclerView.adapter?.itemCount)
        }
        onView(withId(R.id.rv_facts)).check(matches(isDisplayed()))

    }
}