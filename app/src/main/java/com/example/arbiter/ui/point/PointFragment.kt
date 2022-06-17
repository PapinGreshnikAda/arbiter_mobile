package com.example.arbiter.ui.point

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.arbiter.R
import com.example.arbiter.databinding.FragmentPointBinding


class PointFragment : Fragment() {
    private var _binding: FragmentPointBinding? = null
    private var _viewModel: PointViewModel? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewModel =
            ViewModelProvider(this).get(PointViewModel::class.java)
        _binding = FragmentPointBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.body.setOnClickListener { viewModel.onTap() }

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Initial -> initial(it)
                Middle -> animationMiddle()
                Start -> animationStart()
                is End -> showAnswer(it)
            }
        }

        return root
    }

    private fun initial(state: Initial) {
        binding.pointCenter.visibility = View.INVISIBLE
        binding.answerText.text = state.text
    }

    private fun animationStart() {
        binding.answerText.text = ""
        binding.pointCenter.visibility = View.INVISIBLE

        val animation = AnimationUtils.loadAnimation(context, R.anim.point_shaking_animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                viewModel.onShakeEnd()
            }
        })

        binding.pointImage.animation = animation
    }

    private fun animationMiddle() {
        binding.pointCenter.visibility = View.VISIBLE
        val animation = AnimationUtils.loadAnimation(context, R.anim.center_scale_animation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(arg0: Animation) {}
            override fun onAnimationRepeat(arg0: Animation) {}
            override fun onAnimationEnd(arg0: Animation) {
                viewModel.onAnimationEnd()
            }
        })

        binding.pointCenter.animation = animation
    }

    private fun showAnswer(state: End) {
        binding.answerText.text = state.text
    }

    override fun onPause() {
        super.onPause()
        viewModel.end()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}