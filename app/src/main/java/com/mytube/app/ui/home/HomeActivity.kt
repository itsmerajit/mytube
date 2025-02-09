package com.mytube.app.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mytube.app.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            binding = ActivityHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            setupDummyUI()
            Timber.d("HomeActivity: Dummy UI initialized successfully")
        } catch (e: Exception) {
            Timber.e(e, "HomeActivity: Critical error during onCreate()")
            throw e
        }
    }

    private fun setupDummyUI() {
        // Dummy implementation of UI setup
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.d("Dummy Search: Query submitted - $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Timber.d("Dummy Search: Text changed - $newText")
                return true
            }
        })

        // Dummy data generation
        val dummyMediaItems = listOf(
            "Video 1", "Video 2", "Music Track", 
            "Podcast", "Short Clip", "Documentary"
        )

        // TODO: Implement actual adapter and RecyclerView setup
        Timber.d("Dummy Media Items: $dummyMediaItems")
    }
}
