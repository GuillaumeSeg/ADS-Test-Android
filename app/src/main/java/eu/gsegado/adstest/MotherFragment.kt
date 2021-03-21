package eu.gsegado.adstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.gsegado.adstest.databinding.MotherFragmentBinding

/**
 *  MotherFragment, contains a button, a list (purple) and a Child fragment
 */
class MotherFragment : Fragment() {
    private lateinit var binding: MotherFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MotherFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onDestroyView() {
        // Clear subscription for the content view
        binding.motherContent.clear()

        super.onDestroyView()
    }
}