package com.mytube.app.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytube.app.data.MediaRepository
import com.mytube.app.models.MediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {
    private val mediaRepository = MediaRepository()

    private val _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> = _mediaItems.asStateFlow()

    init {
        loadInitialMedia()
    }

    private fun loadInitialMedia() {
        viewModelScope.launch {
            Timber.d("HomeViewModel: Loading initial media")
            mediaRepository.getMediaItems().collect { items ->
                Timber.i("HomeViewModel: Loaded ${items.size} initial media items")
                _mediaItems.value = items
            }
        }
    }

    fun searchMedia(query: String) {
        viewModelScope.launch {
            Timber.d("HomeViewModel: Searching media with query: $query")
            mediaRepository.searchMediaItems(query).collect { filteredItems ->
                Timber.i("HomeViewModel: Found ${filteredItems.size} items matching query")
                _mediaItems.value = filteredItems
            }
        }
    }
}
