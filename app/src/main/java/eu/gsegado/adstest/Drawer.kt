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
import eu.gsegado.adstest.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar

class Drawer(context: Context, attrs: AttributeSet): NavigationView(context, attrs) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var binding: DrawerHeaderBinding

    private val items : MutableList<String> = mutableListOf()
    private lateinit var itemsAdapter: ArrayAdapter<String>

    init {
        // Inflate binding
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DrawerHeaderBinding.inflate(inflater, this, true)

        initAdapter()

        val mainActivity: MainActivity = context as MainActivity

        // Send the event when tapping the FAB
        binding.button.setOnClickListener {
            mainActivity.eventSubject.onNext(Constants.EVENT)
        }

        // Observer
        mainActivity.eventSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            formatTimeCapture()
                        },
                        onError = {
                            Log.e(Drawer::class.simpleName, "Error while receiving an event")
                        }
                ).addTo(compositeDisposable)
    }

    // Save the state of the screen, especially the items in the list if the user flips the
    // phone screen and destroys the activity.
    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putStringArrayList(Constants.ITEMS_STATE_KEY, ArrayList(items))
        bundle.putParcelable(Constants.SUPERSTATE_KEY, super.onSaveInstanceState())
        return bundle
    }

    // Restore the items of the lists
    override fun onRestoreInstanceState(state: Parcelable?) {
        var viewState = state
        if (viewState is Bundle) {
            viewState.getStringArrayList(Constants.ITEMS_STATE_KEY)?.let {
                items.addAll(it.toMutableList())
                itemsAdapter.notifyDataSetChanged()
            }
            viewState = viewState.getParcelable(Constants.SUPERSTATE_KEY)
        }
        super.onRestoreInstanceState(viewState)
    }

    /// - PUBLIC METHODS

    fun clear() {
        compositeDisposable.clear()
    }

    /// - PRIVATE METHODS

    private fun formatTimeCapture() {
        val time = Calendar.getInstance().timeInMillis
        items.add("${items.size + 1} - $time")

        itemsAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        itemsAdapter =  ArrayAdapter<String>(binding.root.context, R.layout.item_list, items)
        binding.list.adapter = itemsAdapter
        binding.list.divider = null
    }
}