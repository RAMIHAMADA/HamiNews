package com.rami.haminews.ui.detalis

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rami.haminews.R
import com.rami.haminews.databinding.FragmentDetalisBinding

class DetailsFragment : Fragment(R.layout.fragment_detalis) {
    private val binding: FragmentDetalisBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
