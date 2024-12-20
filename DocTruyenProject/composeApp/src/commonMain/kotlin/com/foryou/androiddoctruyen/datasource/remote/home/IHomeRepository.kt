package com.foryou.androiddoctruyen.datasource.remote.home

import com.foryou.androiddoctruyen.datasource.remote.model.StoryModel

interface IHomeRepository {
    suspend fun getHome(): Result<List<StoryModel>>
}