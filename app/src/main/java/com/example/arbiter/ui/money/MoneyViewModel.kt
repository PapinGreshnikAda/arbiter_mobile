package com.example.arbiter.ui.money

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MoneyViewModel : ViewModel() {
    private val _state = MutableLiveData<MoneyAnimateState>().apply {
        value = Initial
    }

    val state: LiveData<MoneyAnimateState> = _state

    fun onTap() {
        if (state.value !is Start) {
            _state.value = Start
        } else {
            val value = Random.nextInt(100)
            _state.value = End(value % 2 == 0)
        }
    }

    fun end() {
        _state.value = Initial
    }
}