package eu.gsegado.adstest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.gsegado.adstest.databinding.MotherFragmentBinding

// TODO - Height fragment en dur...
class MotherFragment : Fragment() {
    private lateinit var binding: MotherFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MotherFragmentBinding.inflate(inflater)

        // Adapter

        return binding.root
    }
}