package eu.gsegado.adstest

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import eu.gsegado.adstest.databinding.ActivityMainBinding
import eu.gsegado.adstest.databinding.DrawerHeaderBinding
import java.util.Calendar

class MainActivity : FragmentActivity() {
    private val items : MutableList<String> = mutableListOf()
    private val itemsAdapter : ArrayAdapter<String> by lazy { ArrayAdapter<String>(this, R.layout.item_list, items) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Navigation Drawer
        val navHeaderBinding: DrawerHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.drawer_header, binding.navigationDrawer, true)
        navHeaderBinding.button.setOnClickListener {
            val time = Calendar.getInstance().timeInMillis
            items.add("${items.size+1} - $time")

            itemsAdapter.notifyDataSetChanged()
        }

        // Adapter
        navHeaderBinding.list.adapter = itemsAdapter
        navHeaderBinding.list.divider = null

    }

}