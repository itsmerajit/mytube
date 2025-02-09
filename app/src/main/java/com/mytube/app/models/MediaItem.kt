package com.mytube.app.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaItem(
    val id: String,
    val title: String,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val videoUrl: String? = null,
    val duration: Long = 0,
    val viewCount: Int = 0,
    val uploadDate: String? = null
) : Parcelable {
    companion object {
        fun createDummyItems(): List<MediaItem> = listOf(
            MediaItem(
                id = "1",
                title = "Introduction to MyTube",
                description = "First video in MyTube app",
                thumbnailUrl = "https://example.com/thumb1.jpg",
                duration = 120,
                viewCount = 1000
            ),
            MediaItem(
                id = "2",
                title = "Android Development Tips",
                description = "Advanced techniques for Android developers",
                thumbnailUrl = "https://example.com/thumb2.jpg",
                duration = 300,
                viewCount = 5000
            )
        )
    }
}
