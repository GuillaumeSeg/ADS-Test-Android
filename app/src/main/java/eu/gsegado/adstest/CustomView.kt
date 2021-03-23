package eu.gsegado.adstest

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import eu.gsegado.adstest.databinding.ItemViewBinding
import eu.gsegado.adstest.utils.Constants
import eu.gsegado.adstest.utils.EspressoIdlingResource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar

/**
 * Custom view with a button and a list
 */
class CustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var binding: ItemViewBinding

    private var items : MutableList<String> = mutableListOf()
    private var itemsAdapter: ArrayAdapter<String>

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemViewBinding.inflate(inflater, this, true)

        itemsAdapter =  ArrayAdapter<String>(binding.root.context, R.layout.item_list, items)
        binding.list.adapter = itemsAdapter
        binding.list.divider = null

        // Send the event when tapping the FAB
        binding.button.setOnClickListener {
            val mainActivity: MainActivity = context as MainActivity
            mainActivity.eventSubject.onNext(Unit)

            // only used for the tests
            EspressoIdlingResource.increment()
        }

        // Observer
        (context as MainActivity).eventSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    formatTimeCapture()
                },
                onError = {
                    Log.e(CustomView::class.simpleName, "Error while receiving an event")
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
        items.add("${items.size+1} - $time")

        itemsAdapter.notifyDataSetChanged()
        // only used for the tests
        EspressoIdlingResource.decrement()
    }
}