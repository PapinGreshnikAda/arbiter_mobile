package com.example.arbiter.ui.point

sealed interface PointAnimateState

object Initial : PointAnimateState {
    const val text = "Задай вопрос, нажми на шар и узнай ответ!"
}

object Start : PointAnimateState
object Middle : PointAnimateState
class End(val text: String) : PointAnimateState
