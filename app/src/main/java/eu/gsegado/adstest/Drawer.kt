package eu.gsegado.adstest

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.google.android.material.navigation.NavigationView
import eu.gsegado.adstest.databinding.DrawerHeaderBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar

class Drawer(context: Context, attrs: AttributeSet): NavigationView(context, attrs) {
    private var binding: DrawerHeaderBinding

    private val items : MutableList<String> = mutableListOf()
    private lateinit var itemsAdapter: ArrayAdapter<String>

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DrawerHeaderBinding.inflate(inflater, this, true)

        val mainActivity: MainActivity = context as MainActivity
        binding.button.setOnClickListener {
            mainActivity.eventSubject.onNext(1)
        }

        itemsAdapter =  ArrayAdapter<String>(binding.root.context, R.layout.item_list, items)
        binding.list.adapter = itemsAdapter
        binding.list.divider = null

        // Observer
        // TODO - close clean dispose alert !
        mainActivity.eventSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            formatTimeCapture()
                        },
                        onError = {
                            Log.e("Drawer", "Error while receiving an event")
                        }
                )
    }

    override fun onSaveInstanceState(): Parcelable {
        // TODO - add Key const static bla
        val bundle = Bundle()
        bundle.putStringArrayList("items", ArrayList(items))
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        // TODO - add Key const static bla
        var viewState = state
        if (viewState is Bundle) {
            viewState.getStringArrayList("items")?.let {
                items.addAll(it.toMutableList())
                itemsAdapter.notifyDataSetChanged()
            }
            viewState = viewState.getParcelable("superState")
        }
        super.onRestoreInstanceState(viewState)
    }

    private fun formatTimeCapture() {
        val time = Calendar.getInstance().timeInMillis
        items.add("${items.size + 1} - $time")

        itemsAdapter.notifyDataSetChanged()
    }
}