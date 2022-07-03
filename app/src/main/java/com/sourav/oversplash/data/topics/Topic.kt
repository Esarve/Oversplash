package com.sourav.oversplash.data.topics

data class Topic(
    val cover_photo: CoverPhoto,
    val current_user_contributions: List<Any>,
    val description: String,
    val ends_at: Any,
    val featured: Boolean,
    val id: String,
    val links: LinksXX,
    val only_submissions_after: Any,
    val owners: List<Owner>,
    val preview_photos: List<PreviewPhoto>,
    val published_at: String,
    val slug: String,
    val starts_at: String,
    val status: String,
    val title: String,
    val total_current_user_submissions: Any,
    val total_photos: Int,
    val updated_at: String,
    val visibility: String
)