package eu.gsegado.adstest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import eu.gsegado.adstest.databinding.ItemViewBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar

class CustomView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    private var binding: ItemViewBinding

    private val items : MutableList<String> = mutableListOf()
    private lateinit var itemsAdapter: ArrayAdapter<String>

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemViewBinding.inflate(inflater, this, true)

        itemsAdapter =  ArrayAdapter<String>(binding.root.context, R.layout.item_list, items)
        binding.list.adapter = itemsAdapter

        binding.button.setOnClickListener {
            val mainActivity: MainActivity = context as MainActivity

            mainActivity.eventSubject.onNext(2)
        }

        // Observer
        // TODO - close clean dispose alert !
        (context as MainActivity).eventSubject
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    formatTimeCapture()
                },
                onError = {
                    Log.e("FragmentMother", "Error while receiving an event")
                }
            )
    }

    private fun formatTimeCapture() {
        val time = Calendar.getInstance().timeInMillis
        items.add("${items.size+1} - $time")

        itemsAdapter.notifyDataSetChanged()
    }
}