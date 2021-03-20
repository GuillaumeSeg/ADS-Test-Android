package eu.gsegado.adstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import eu.gsegado.adstest.databinding.MotherFragmentBinding

class MotherFragment : Fragment() {
    private lateinit var binding: MotherFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MotherFragmentBinding.inflate(inflater)

        binding.motherContent.rootView.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.mother_fragment_background))

        return binding.root
    }

}