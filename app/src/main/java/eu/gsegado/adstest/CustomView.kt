package eu.gsegado.adstest

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import eu.gsegado.adstest.databinding.ItemViewBinding

class CustomView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    private var binding: ItemViewBinding

    init {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ItemViewBinding.inflate(inflater, this, true)
    }
}