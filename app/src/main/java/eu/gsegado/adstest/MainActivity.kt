package eu.gsegado.adstest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import eu.gsegado.adstest.databinding.ActivityMainBinding
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 *  Activity that contains the navigation Drawer and two mother fragments
 */
class MainActivity : FragmentActivity() {

    val eventSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    private lateinit var binding: ActivityMainBinding

    // listener used to move the content of the activity with Drawer opening/closing
    private val actionBarDrawerToggle: ActionBarDrawerToggle by lazy {
        object : ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)

                // Move the first guideline from 0% to 33% of the screen according to the slideOffset
                var guideLine: Guideline = binding.guidelineVert1
                var params = guideLine.layoutParams as ConstraintLayout.LayoutParams
                params.guidePercent = 0.33f*slideOffset
                guideLine.layoutParams = params
                // --
                // Move the second guideline from 50% to 66% of the screen according to the slideOffset
                guideLine = binding.guidelineVert2
                params = guideLine.layoutParams as ConstraintLayout.LayoutParams
                params.guidePercent = 0.16f*slideOffset+0.5f
                guideLine.layoutParams = params
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Size of Navigation Drawer, it should be equal to 1/3 of the screen (landscape or portrait)
        val width = resources.displayMetrics.widthPixels / 3
        val drawerParams: DrawerLayout.LayoutParams = binding.navigationDrawer.layoutParams as DrawerLayout.LayoutParams
        drawerParams.width = width
        binding.navigationDrawer.layoutParams = drawerParams

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
    }

    override fun onDestroy() {
        super.onDestroy()

        binding.drawerLayout.removeDrawerListener(actionBarDrawerToggle)

        // Clear subscriptions for Drawer
        binding.navigationDrawer.clear()

    }
}