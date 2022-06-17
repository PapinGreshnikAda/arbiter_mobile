package com.example.arbiter.ui.money

sealed interface MoneyAnimateState

object Initial : MoneyAnimateState
object Start : MoneyAnimateState
class End(val isRub: Boolean) : MoneyAnimateState
