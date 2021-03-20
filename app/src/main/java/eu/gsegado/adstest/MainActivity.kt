package eu.gsegado.adstest

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import eu.gsegado.adstest.databinding.ActivityMainBinding
import eu.gsegado.adstest.databinding.DrawerHeaderBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.Calendar

class MainActivity : FragmentActivity() {

    val eventSubject: BehaviorSubject<Int> = BehaviorSubject.create()

    private val items : MutableList<String> = mutableListOf()
    private val itemsAdapter : ArrayAdapter<String> by lazy { ArrayAdapter<String>(this, R.layout.item_list, items) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate binding
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Navigation Drawer
        val navHeaderBinding: DrawerHeaderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.drawer_header, binding.navigationDrawer, true)
        navHeaderBinding.button.setOnClickListener {
            eventSubject.onNext(1)
        }

        // Adapter
        navHeaderBinding.list.adapter = itemsAdapter
        navHeaderBinding.list.divider = null

        // Observer
        // TODO - close clean dispose alert !
        eventSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    formatTimeCapture()
                },
                onError = {
                    Log.e("MainActivity", "Error while receiving an event")
                }
            )
    }

    private fun formatTimeCapture() {
        val time = Calendar.getInstance().timeInMillis
        items.add("${items.size+1} - $time")

        itemsAdapter.notifyDataSetChanged()
    }

}