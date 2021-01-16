package com.miguel.fastassignment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.fastassignment.data.Repository
import com.miguel.fastassignment.data.network.NetworkResponse
import kotlinx.coroutines.*

internal class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {
    val viewState: LiveData<ViewState>
        get() = _viewState
    private val _viewState = MutableLiveData<ViewState>(ViewState.Empty)

    private var searchTerm: String? = null

    private var queryTextDebouncer: Job? = null
    fun searchTermChanged(query: String?) {
        searchTerm = query
        networkJob?.cancel()

        if (searchTerm.isNullOrBlank()) {
            _viewState.value = ViewState.Empty
        } else {
            queryTextDebouncer?.cancel()

            queryTextDebouncer = viewModelScope.launch {
                delay(500)
                performSearch(searchTerm!!)
            }
        }
    }

    private var networkJob: Job? = null
    private fun performSearch(query: String) {
        _viewState.value = ViewState.Loading

        networkJob?.cancel()
        networkJob = viewModelScope.launch(Dispatchers.IO) {
            val networkResponse = repository.search(query)
            withContext(Dispatchers.Main) {
                _viewState.value = when (networkResponse) {
                    is NetworkResponse.SUCCESS -> ViewState.Result(networkResponse.results)
                    is NetworkResponse.API_ERROR -> ViewState.Error(networkResponse.exception.message)
                    is NetworkResponse.NETWORK_ERROR -> ViewState.Error("Network Error please retry.")
                }
            }
        }
    }
}
