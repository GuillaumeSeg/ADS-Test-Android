package eu.gsegado.adstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.gsegado.adstest.databinding.ChildFragmentBinding

class ChildFragment : Fragment() {
    private lateinit var binding: ChildFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ChildFragmentBinding.inflate(inflater)

        return binding.root
    }

}