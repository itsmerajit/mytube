package com.mytube.app.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@ViewModelScoped
class MediaRepository @Inject constructor() {

    private val _dummyItems = MutableStateFlow(listOf(
        "Funny Cat Video", 
        "Travel Vlog", 
        "Cooking Tutorial", 
        "Music Performance", 
        "Tech Review", 
        "Gaming Highlight"
    ))
    val dummyItems = _dummyItems.asStateFlow()

    fun getDummyMediaItems(): Flow<List<String>> = flow {
        val items = _dummyItems.value
        Timber.d("Dummy Media Repository: Emitting ${items.size} items")
        emit(items)
    }

    fun searchMedia(query: String): Flow<List<String>> = flow {
        val filteredMedia = _dummyItems.value.filter { it.contains(query, ignoreCase = true) }
        Timber.d("Dummy Search: Query '$query' returned ${filteredMedia.size} results")
        emit(filteredMedia)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object MediaRepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMediaRepository(): MediaRepository = MediaRepository()
}
