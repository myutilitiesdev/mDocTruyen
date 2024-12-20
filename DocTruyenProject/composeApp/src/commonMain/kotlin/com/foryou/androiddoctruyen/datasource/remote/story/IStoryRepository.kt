package com.foryou.androiddoctruyen.datasource.remote.story

import com.foryou.androiddoctruyen.datasource.remote.model.CategoryModel
import com.foryou.androiddoctruyen.datasource.remote.model.StoryModel
import com.foryou.androiddoctruyen.utils.UiState
import kotlinx.coroutines.flow.Flow

interface IStoryRepository {
    suspend fun getCategories(filter: String): Flow<UiState<List<CategoryModel>>>
    suspend fun getStoryBasedCategory(storyId: String): List<StoryModel>
}