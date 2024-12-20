package com.foryou.androiddoctruyen.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(
    val id: Long,
    val name: String,
    val slug: String,
    val position: Long,
    val type: String,
    val image: List<CategoryImage>?,
    val rootId: String? = null,
    val parentId: String? = null,

    var storyList: List<StoryModel>? = null
)

@Serializable
data class CategoryImage(
    val uid: String,
    val url: String,
    val fileName: String,
)