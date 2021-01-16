package com.miguel.fastassignment

import com.miguel.fastassignment.data.Movie

sealed class ViewState {
    object Empty : ViewState()
    data class Result(val result: List<Movie>) : ViewState()
    object Loading : ViewState()
    data class Error(val message: String) : ViewState()
}