package com.foryou.androiddoctruyen.presenter.screens.main.tabs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foryou.androiddoctruyen.datasource.remote.model.StoryModel
import com.foryou.androiddoctruyen.datasource.remote.story.StoryRepository
import com.foryou.androiddoctruyen.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HotVM(private val repo: StoryRepository) : ViewModel() {
    private val _storiesList = MutableStateFlow<List<StoryModel>>(emptyList())
    val storiesList get() = _storiesList.asStateFlow()

    private val _uiState = mutableStateOf<UiState<String>>(UiState.Loading)
    val uiState: State<UiState<String>> = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> get() = _isRefreshing

    private var skip = 0
    private val limit = 10
    private var hasMoreData = true // Flag to indicate if more data is available

    init {
        _uiState.value = UiState.Loading

        getHotStories()
    }

    // Fetch data (refreshing or loading more)
    fun getHotStories(isRefresh: Boolean = false) {

        // Stop if already loading or no more data
        if (_isLoading.value || !hasMoreData) return

        if (isRefresh) {
            _isRefreshing.value = true
            skip = 0 // Reset page on refresh
        } else {
            _isLoading.value = true
        }

        val filter = "{ \"limit\": $limit, \"skip\": $skip, \"where\": { \"storyId\" : \"\" } }"

        viewModelScope.launch {
            repo.getHotStories(filter).onEach {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        if (isRefresh) {
                            _storiesList.value = it.data
                        } else {
                            _storiesList.value += it.data
                        }

                        if (it.data.isEmpty()) {
                            hasMoreData = false // No more data to load
                        } else {
                            skip += limit
                        }

                        _uiState.value = UiState.Success("Data loaded successfully!")

                        _isLoading.value = false
                        _isRefreshing.value = false
                    }

                    is UiState.Error -> {
                        _isLoading.value = false

                        // Handle error
                        _uiState.value = UiState.Error("Failed to load data.")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}