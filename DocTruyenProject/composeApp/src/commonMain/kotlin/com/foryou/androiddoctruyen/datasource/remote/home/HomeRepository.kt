package com.foryou.androiddoctruyen.datasource.remote.home

import com.foryou.androiddoctruyen.datasource.remote.model.StoryModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

class HomeRepository(private val httpClient: HttpClient) : IHomeRepository {
    override suspend fun getHome(): Result<List<StoryModel>> {

        val httpResponse = httpClient.get {
            url {
                path("/stories")
            }
        }

        return try {
            Result.success(httpResponse.body())
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}