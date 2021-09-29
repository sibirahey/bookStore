package com.kavak.bookstore.dataView

sealed class ScreenState

object LoadingState : ScreenState()

class CompleteState(val sections: List<ViewSection>) : ScreenState()
