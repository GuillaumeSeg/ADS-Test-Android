package eu.gsegado.adstest.utils

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Used by the test suite to handle asynchronous change in the UI
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}