package com.foryou.androiddoctruyen.datasource.remote.story

import com.foryou.androiddoctruyen.datasource.remote.model.CategoryModel
import com.foryou.androiddoctruyen.datasource.remote.model.StoryModel
import com.foryou.androiddoctruyen.utils.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StoryRepository(private val httpClient: HttpClient) : IStoryRepository {
    override suspend fun getCategories(filter: String): Flow<UiState<List<CategoryModel>>> = flow {
        emit(UiState.Loading)

        val httpResponse = httpClient.get {
            url {
                path("/categories")
            }
        }

        try {
            emit(UiState.Success(httpResponse.body()))
        } catch (error: Exception) {
            emit(UiState.Error(error.toString()))
        }
    }

    override suspend fun getStoryBasedCategory(storyId: String): List<StoryModel> {
        try {
            val httpResponse = httpClient.get {
                url {
                    path("/stories")
                    parameter("filter", storyId)
                }
            }

            return httpResponse.body()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }
}