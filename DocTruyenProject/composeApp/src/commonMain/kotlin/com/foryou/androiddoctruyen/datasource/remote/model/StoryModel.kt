package com.foryou.androiddoctruyen.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryModel(
    val id: Long,
    val authorId: Long,
    val image: List<Image>,
    val cover: List<Cover>,
    val name: String,
    val description: String,
    val status: String,
    val tags: List<Long>? = null,
    val categories: List<Long>,
    val content: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,

    var countChapters: Int = 0,
)

@Serializable
data class Cover(
    val uid: String,
    val url: String,
    val mimeType: String? = null,
    val name: String? = null,
    val fileName: String? = null
)

@Serializable
data class Image(
    val uid: String,
    val url: String,
    val mimeType: String? = null,
    val name: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val urls: Urls? = null,
    val fileName: String? = null
)

@Serializable
data class Urls(
    val small: Small,
    val large: Large,
)

@Serializable
data class Small(
    val url: String,
    val width: Long,
    val height: Long,
)

@Serializable
data class Large(
    val url: String,
    val width: Long,
    val height: Long,
)