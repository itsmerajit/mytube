package com.mytube.app.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mytube.app.databinding.ActivityHomeBinding
import com.mytube.app.ui.home.adapters.MediaGridAdapter
import com.mytube.app.ui.home.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mediaGridAdapter: MediaGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        Timber.i("HomeActivity: onCreate() - Initializing UI")
        
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()
        setupToolbar()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        Timber.d("HomeActivity: setupRecyclerView() - Configuring RecyclerView")
        mediaGridAdapter = MediaGridAdapter { mediaItem ->
            Timber.i("HomeActivity: Media item clicked - ${mediaItem.title}")
            // TODO: Implement media item click handling
        }

        binding.mediaGrid.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mediaGridAdapter
        }
    }

    private fun setupSearchView() {
        Timber.d("HomeActivity: setupSearchView() - Configuring SearchView")
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { 
                    Timber.i("HomeActivity: Search Query Submitted - $query")
                    viewModel.searchMedia(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    Timber.d("HomeActivity: Search Text Changed - $it")
                    viewModel.searchMedia(it)
                }
                return true
            }
        })
    }

    private fun setupToolbar() {
        Timber.d("HomeActivity: setupToolbar() - Configuring Toolbar")
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun observeViewModel() {
        Timber.d("HomeActivity: observeViewModel() - Setting up ViewModel Observation")
        lifecycleScope.launch {
            viewModel.mediaItems.collect { mediaItems ->
                Timber.i("HomeActivity: Received ${mediaItems.size} media items")
                mediaGridAdapter.submitList(mediaItems)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.i("HomeActivity: onStart() - Activity becoming visible")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("HomeActivity: onResume() - Activity is in the foreground")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("HomeActivity: onPause() - Activity is losing focus")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("HomeActivity: onStop() - Activity is no longer visible")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("HomeActivity: onDestroy() - Activity is being destroyed")
    }
}
