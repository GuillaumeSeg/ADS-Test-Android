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

class MainActivity : FragmentActivity() {

    // TODO - not an Int but a real event ?
    val eventSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    private lateinit var binding: ActivityMainBinding

    // Slide for the content of the activity with Drawer opening/closing
    private val actionBarDrawerToggle: ActionBarDrawerToggle by lazy {
        object : ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)

                var guideLine: Guideline = binding.guidelineVert1
                var params = guideLine.layoutParams as ConstraintLayout.LayoutParams
                params.guidePercent = 0.33f*slideOffset
                guideLine.layoutParams = params
                // --
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

        // Size of Navigation Drawer
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


    companion object {
        const val ITEMS_STATE_KEY = "items"
        const val SUPERSTATE_KEY = "superState"
    }
}