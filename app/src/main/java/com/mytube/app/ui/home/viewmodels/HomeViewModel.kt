package com.mytube.app.ui.home.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytube.app.data.MediaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
) : ViewModel() {

    private val _mediaItems = MutableStateFlow<List<String>>(emptyList())
    val mediaItems = _mediaItems.asStateFlow()

    init {
        loadDummyMediaItems()
    }

    private fun loadDummyMediaItems() {
        viewModelScope.launch {
            try {
                mediaRepository.getDummyMediaItems().collect { items ->
                    _mediaItems.value = items
                    Timber.d("HomeViewModel: Loaded ${items.size} dummy media items")
                }
            } catch (e: Exception) {
                Timber.e(e, "HomeViewModel: Error loading dummy media items")
            }
        }
    }

    fun searchMedia(query: String) {
        viewModelScope.launch {
            try {
                mediaRepository.searchMedia(query).collect { filteredItems ->
                    _mediaItems.value = filteredItems
                    Timber.d("HomeViewModel: Search returned ${filteredItems.size} items for query '$query'")
                }
            } catch (e: Exception) {
                Timber.e(e, "HomeViewModel: Error during media search")
            }
        }
    }
}
