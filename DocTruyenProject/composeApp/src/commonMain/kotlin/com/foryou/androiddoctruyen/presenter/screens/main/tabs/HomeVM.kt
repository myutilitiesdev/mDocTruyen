package com.foryou.androiddoctruyen.presenter.screens.main.tabs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foryou.androiddoctruyen.datasource.remote.home.HomeRepository
import com.foryou.androiddoctruyen.datasource.remote.model.CategoryModel
import com.foryou.androiddoctruyen.datasource.remote.model.HomeModel
import com.foryou.androiddoctruyen.datasource.remote.story.StoryRepository
import com.foryou.androiddoctruyen.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeVM(
    private val homeRepository: HomeRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    private val _uiState = mutableStateOf<UiState<HomeModel>>(UiState.Loading)
    val uiState: State<UiState<HomeModel>> = _uiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> get() = _isRefreshing

    private var skip = 0
    private val limit = 5
    private var hasMoreData = true // Flag to indicate if more data is available

    init {
        println("init")
        homePage()
    }

    /**
    Recommended Pattern:

    In Repository: Return Flow with states
    In ViewModel: Use onEach + launchIn for state management
    In UI: Use collect to observe and update UI
     **/
    private fun homePage(isRefresh: Boolean = false) {
        // Stop if already loading or no more data
        if (_isLoading.value || !hasMoreData) return

        println("homePage - isRefresh $isRefresh")

        if (isRefresh) {
            _isRefreshing.value = true
            skip = 0 // Reset page on refresh
        } else {
            _isLoading.value = true
        }

        viewModelScope.launch(Dispatchers.Default) {
            val categoryAsync = async { storyRepository.getCategories("") }
            val categoryResult = categoryAsync.await()

            categoryResult.onEach {
                when (it) {
                    is UiState.Loading -> {
                        _isLoading.value = true
                    }

                    is UiState.Success -> {
                        it.data.forEach { categoryItem ->

                            coroutineScope {
                                val filter =
                                    "{ \"where\": { \"categories\" : { \"contains\" : [${categoryItem.id}] } } }"
                                val storyBasedCategory =
                                    async { storyRepository.getStoryBasedCategory(filter) }
                                val storyResult = storyBasedCategory.await()

                                if (storyResult.isNotEmpty()) {
                                    val categoryList: MutableList<CategoryModel> = mutableListOf()

                                    categoryItem.storyList = storyResult

                                    categoryList.add(categoryItem)

                                    if (isRefresh) {
                                        updateCategoryList(categoryList.toList())
                                    } else {
                                        addItems(categoryList.toList())
                                    }

                                    if (it.data.isEmpty()) {
                                        hasMoreData = false // No more data to load
                                    } else {
                                        skip += limit
                                    }
                                }
                            }
                        }

                        _isLoading.value = false
                        _isRefreshing.value = false
                    }

                    is UiState.Error -> {
                        _isLoading.value = false

                        // Handle error
                        _uiState.value = UiState.Error("Failed to load data.")
                    }
                }
            }.catch {
                _uiState.value = UiState.Error("Failed to load data.")
            }.launchIn(viewModelScope)
        }
    }

    private fun updateCategoryList(newCategories: List<CategoryModel>) {
        _uiState.value = UiState.Success(
            HomeModel(
                categoryList = newCategories
            )
        )
    }

    private fun addItems(
        additionalCategories: List<CategoryModel>
    ) {
        val currentState = _uiState.value
        if (currentState is UiState.Success) {
            val currentData = currentState.data
            _uiState.value = UiState.Success(
                HomeModel(
                    categoryList = currentData.categoryList + additionalCategories
                )
            )
        } else {
            _uiState.value = UiState.Success(
                HomeModel(
                    categoryList = additionalCategories
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared")
    }
}