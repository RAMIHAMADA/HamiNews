package com.rami.haminews.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rami.haminews.R
import com.rami.haminews.databinding.FragmentSplashBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding: FragmentSplashBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
    }

    private fun startAnimation() {
        binding.lottieCar.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animator: Animator) {
                startApp()
            }
        })
    }

    private fun startApp() {
        findNavController().navigate(R.id.action_splashFragment_to_tabsFragment)
    }
}
