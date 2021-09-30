package com.kavak.bookstore.dataView

sealed class ScreenState

object LoadingState : ScreenState()

object NoBooksState : ScreenState()

object NoBestSellersState : ScreenState()

class CompleteState(val sections: List<ViewSection>) : ScreenState()
