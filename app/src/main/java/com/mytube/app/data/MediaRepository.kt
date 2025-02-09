package com.mytube.app.data

import com.mytube.app.models.MediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MediaRepository {
    fun getMediaItems(): Flow<List<MediaItem>> = flow {
        Timber.d("MediaRepository: Fetching media items")
        try {
            // Simulating network or database fetch
            val dummyItems = MediaItem.createDummyItems()
            Timber.i("MediaRepository: Loaded ${dummyItems.size} media items")
            emit(dummyItems)
        } catch (e: Exception) {
            Timber.e(e, "Error fetching media items")
            emit(emptyList())
        }
    }

    fun searchMediaItems(query: String): Flow<List<MediaItem>> = flow {
        Timber.d("MediaRepository: Searching media items with query: $query")
        val allItems = MediaItem.createDummyItems()
        val filteredItems = allItems.filter { 
            it.title.contains(query, ignoreCase = true) || 
            it.description?.contains(query, ignoreCase = true) == true
        }
        Timber.i("MediaRepository: Found ${filteredItems.size} items matching query")
        emit(filteredItems)
    }
}
