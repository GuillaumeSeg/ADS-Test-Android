package eu.gsegado.adstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.gsegado.adstest.databinding.ChildFragmentBinding

/**
 *  ChildFragment, contains 2 buttons, 2 lists (1 orange / 1 blue)
 */
class ChildFragment : Fragment() {
    private lateinit var binding: ChildFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ChildFragmentBinding.inflate(inflater)

        return binding.root
    }

    override fun onDestroyView() {
        // Clear subscriptions for the content views
        binding.childContent.clear()
        binding.finalViewContent.clear()

        super.onDestroyView()
    }

}