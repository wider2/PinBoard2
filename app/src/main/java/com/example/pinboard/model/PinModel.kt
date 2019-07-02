package com.example.pinboard.model

data class PinModel(
    val id: String,
    val created_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    val liked_by_user: Boolean,
    val user: User,
    val urls: Urls,
    val categories: List<Categories> = listOf(),
    val links: Links
)

data class User(
    val id: String,
    val username: String,
    val name: String,
    val profile_image: ProfileImage,
    val links: UserLinks
)

data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String
)

data class UserLinks(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class Links(
    val self: String,
    val html: String,
    val download: String
)

data class Categories(
    val id: Int,
    val title: String,
    val photo_count: Int,
    val links: CategoryLinks
)

data class CategoryLinks(
    val self: String,
    val photos: String
)
