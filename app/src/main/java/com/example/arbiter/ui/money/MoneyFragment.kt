package com.example.arbiter.ui.money

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.arbiter.R
import com.example.arbiter.databinding.FragmentMoneyBinding


class MoneyFragment : Fragment() {
    private var _viewModel: MoneyViewModel? = null
    private var _binding: FragmentMoneyBinding? = null
    private val binding get() = _binding!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewModel = ViewModelProvider(this).get(MoneyViewModel::class.java)
        _binding = FragmentMoneyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.body.setOnClickListener {
            viewModel.onTap()
        }

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                Initial -> initial()
                Start -> startAnimation()
                is End -> endAnimation(it)
            }
        }

        return root
    }

    private fun initial() {
        binding.animationPlaceholder.setBackgroundResource(R.drawable.ic_money_rub)
    }

    private fun startAnimation() {
        binding.animationPlaceholder.setBackgroundResource(R.drawable.money_animation)
        val mAnimationDrawable = binding.animationPlaceholder.background as AnimationDrawable
        mAnimationDrawable.start()
    }

    private fun endAnimation(state: End) {
        val placeholder = binding.animationPlaceholder.background
        if (placeholder is AnimationDrawable) {
            placeholder.stop()
            val image = if (state.isRub) R.drawable.ic_money_rub else  R.drawable.ic_money_orel
            binding.animationPlaceholder.setBackgroundResource(image)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.end()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _viewModel = null
    }
}