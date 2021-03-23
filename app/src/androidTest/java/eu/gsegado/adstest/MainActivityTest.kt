package eu.gsegado.adstest

import android.content.pm.ActivityInfo
import android.widget.ListView
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import eu.gsegado.adstest.utils.EspressoIdlingResource
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.AnyOf.anyOf
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test with espresso to test several scenarii
 * and that the features are intended to work,
 * Tests are made in the MainActivity
 *
 * Scenarii :
 * - Click on a FAB and check content of the list
 * - Click on a FAB and check the content of another list
 * - Open the drawer and click on a FAB
 * - Click twice on the same FAB
 * - Click twice on a FAB and twice on another FAB
 * - Click on a FAB and Flip the screen
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    // -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

    @Test
    fun test_clickButton_ChildContent_MotherRight_add_items_adapter_ChildContentList_MotherRight() {

        // Click on FAB in FragmentMother Right -> FragmentChild content
        onView(allOf(
            withId(R.id.button),
            isDescendantOfA(withId(R.id.child_content)),
            isDescendantOfA(withId(R.id.fragment_mother_right))
        )).perform(click())

        onView(allOf(
            withId(R.id.list),
            isDescendantOfA(withId(R.id.child_content)),
            isDescendantOfA(withId(R.id.fragment_mother_right))
        )).check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            // An timestamp item should be added in the list in FragmentMother Right -> FragmentChild content
            assertTrue(view is ListView && view.adapter != null && view.adapter.count > 0)
        }
    }

    @Test
    fun test_clickButton_ChildContent_MotherRight_add_items_adapter_MotherLeftContent() {

        // Click on FAB in FragmentMother Right -> FragmentChild content
        onView(allOf(
            withId(R.id.button),
            isDescendantOfA(withId(R.id.child_content)),
            isDescendantOfA(withId(R.id.fragment_mother_right))
        )).perform(click())

        onView(allOf(
            withId(R.id.list),
            isDescendantOfA(withId(R.id.mother_content)),
            isDescendantOfA(withId(R.id.fragment_mother_left))
        )).check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            // A timestamp item should also be added in the list in FragmentMother Left content
            assertTrue(view is ListView && view.adapter != null && view.adapter.count > 0)
        }
    }

    @Test
    fun test_flipScreen_items_should_still_be_there() {

        // Click on FAB in FragmentMother Right content
        onView(allOf(
            withId(R.id.button),
            isDescendantOfA(withId(R.id.mother_content)),
            isDescendantOfA(withId(R.id.fragment_mother_right))
        )).perform(click())

        // Flip the screen
        activityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(allOf(
            withId(R.id.list),
            isDescendantOfA(withId(R.id.child_content)),
            isDescendantOfA(withId(R.id.fragment_mother_left))
        )).check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            // when activity is created again items in adapter have to be added
            assertTrue(view is ListView && view.adapter != null && view.adapter.count > 0)
        }
    }

    @Test
    fun test_buttonDrawer_click_should_add_item_in_other_view() {
        onView(withId(R.id.drawer_layout)).perform(open())

        onView(
            anyOf(withId(R.id.button_drawer))
        ).perform(click())

        onView(allOf(
            withId(R.id.list),
            isDescendantOfA(withId(R.id.mother_content)),
            isDescendantOfA(withId(R.id.fragment_mother_right))
        )).check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            // An item in the mother right fragment content has to be added
            assertTrue(view is ListView && view.adapter != null && view.adapter.count > 0)
        }
    }

    @Test
    fun test_click_twice_on_a_button() {
        // Click twice on the same button
        for (i in 0..1) {
            onView(allOf(
                withId(R.id.button),
                isDescendantOfA(withId(R.id.mother_content)),
                isDescendantOfA(withId(R.id.fragment_mother_right))
            )).perform(click())
        }

        onView(allOf(
            withId(R.id.list),
            isDescendantOfA(withId(R.id.mother_content)),
            isDescendantOfA(withId(R.id.fragment_mother_right))
        )).check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            // Assert that they are at least 2 items
            assertTrue(view is ListView && view.adapter != null && view.adapter.count > 1)
        }
    }

    @Test
    fun test_click_twice_on_a_button_and_twice_on_another() {
        // Click twice on the same button
        for (i in 0..1) {
            onView(allOf(
                withId(R.id.button),
                isDescendantOfA(withId(R.id.mother_content)),
                isDescendantOfA(withId(R.id.fragment_mother_right))
            )).perform(click())
            onView(allOf(
                withId(R.id.button),
                isDescendantOfA(withId(R.id.child_content)),
                isDescendantOfA(withId(R.id.fragment_mother_left))
            )).perform(click())
        }

        onView(allOf(
            withId(R.id.list),
            isDescendantOfA(withId(R.id.mother_content)),
            isDescendantOfA(withId(R.id.fragment_mother_right))
        )).check { view, noViewFoundException ->
            noViewFoundException?.apply {
                throw this
            }
            // Assert that they are at least 2 items
            assertTrue(view is ListView && view.adapter != null && view.adapter.count > 3)
        }
    }
}


