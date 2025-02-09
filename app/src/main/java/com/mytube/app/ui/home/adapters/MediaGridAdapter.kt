package com.mytube.app.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mytube.app.databinding.ItemMediaBinding
import com.mytube.app.models.MediaItem
import timber.log.Timber

class MediaGridAdapter(
    private val onItemClicked: (MediaItem) -> Unit
) : ListAdapter<MediaItem, MediaGridAdapter.MediaViewHolder>(MediaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemMediaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MediaViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MediaViewHolder(
        private val binding: ItemMediaBinding,
        private val onItemClicked: (MediaItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaItem: MediaItem) {
            binding.apply {
                title.text = mediaItem.title
                // Add more binding logic here
                root.setOnClickListener {
                    Timber.d("MediaGridAdapter: Item clicked - ${mediaItem.title}")
                    onItemClicked(mediaItem)
                }
            }
        }
    }

    class MediaDiffCallback : DiffUtil.ItemCallback<MediaItem>() {
        override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
            return oldItem == newItem
        }
    }
}
