package com.shaun.androidtesting.ui_testing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shaun.androidtesting.R
import com.shaun.androidtesting.presentation.note_list.NotesListFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTesting {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationToEditScreen() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        launchFragmentInHiltContainer<NotesListFragment>() {
            navController.setGraph(R.navigation.main_nav)
            Navigation.setViewNavController(this.requireView(), navController)
        }
        Espresso.onView(
            withId(R.id.addNoteButton).firstView()
        ).perform(ViewActions.click())
        assert(navController.currentDestination?.id == R.id.notesListFragment)
    }

}
