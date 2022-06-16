package com.example.arbiter.ui.point

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class PointViewModel : ViewModel() {
    private val answers = listOf(
        "Да",
        "Определенно да",
        "Есть сомнения",
        "Спроси у мамы",
        "Спроси снова",
        "Нет",
        "Точно нет"
    )

    private val _state = MutableLiveData<PointAnimateState>().apply {
        value = Initial
    }

    val state: LiveData<PointAnimateState> = _state

    fun onTap() {
        _state.value = Start
    }

    fun onShakeEnd() {
        _state.value = Middle
    }

    fun onAnimationEnd() {
        val randomIndex = Random.nextInt(answers.size)
        _state.value = End(answers[randomIndex])
    }
}
