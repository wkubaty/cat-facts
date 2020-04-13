package com.kubaty.catfacts.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.kubaty.catfacts.FakeViewModelFactory
import com.kubaty.catfacts.R
import com.kubaty.catfacts.TestFactsApplication
import com.kubaty.catfacts.di.DaggerTestFactsComponent
import com.kubaty.catfacts.util.CatFactUtil.DUMMY_CAT_FACT
import com.kubaty.catfacts.util.JsonUtil
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4ClassRunner::class)
class FactDetailsFragmentTest @Inject constructor() {
    @Inject
    lateinit var viewModelFactory: FakeViewModelFactory

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Test
    fun test_isFactDetailsDisplayed() {
        val app =
            InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TestFactsApplication
        DaggerTestFactsComponent.builder().application(app).build().apply { inject(app) }

        val testFact = DUMMY_CAT_FACT
        launchFragmentInContainer<FactDetailsFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = bundleOf("fact" to testFact)
        )
        onView(withId(R.id.tv_details_text)).check(matches(withText(testFact.text)))
        onView(withId(R.id.tv_details_update_date)).check(matches(withText(testFact.updatedAt.toString())))
    }

}