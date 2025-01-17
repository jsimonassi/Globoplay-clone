package com.simonassi.globoplay.ui.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.simonassi.globoplay.data.movie.Movie
import androidx.recyclerview.widget.ListAdapter
import com.simonassi.globoplay.BuildConfig
import com.simonassi.globoplay.databinding.ListItemCarouselBinding
import com.simonassi.globoplay.utilities.contants.ImageQualitySpec
import com.simonassi.globoplay.utilities.contants.ItemType

class CarouselAdapter : ListAdapter<Movie, RecyclerView.ViewHolder>(CarouselDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CarouselViewHolder(
            ListItemCarouselBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movie = getItem(position)
        (holder as CarouselViewHolder).bind(movie)
    }

    class CarouselViewHolder(
        private val binding: ListItemCarouselBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {

            binding.showContentButton.setOnClickListener {
                binding.carouselItem?.let { movie ->
                    navigateToHighLights(it, movie)
                }
            }
        }

        private fun navigateToHighLights(view: View, movie: Movie) { 
            val direction = HomeFragmentDirections.actionHomePagerFragmentToHighlightsActivity(
                movie.id,
                ItemType.MOVIE
            )
            view.findNavController().navigate(direction)
        }

        fun bind(item: Movie) {
            binding.apply {
                item.cover = BuildConfig.BUCKET_URL + ImageQualitySpec.LOW_QUALITY + item.cover
                item.backdropCover = BuildConfig.BUCKET_URL + ImageQualitySpec.ORIGINAL_SIZE + item.backdropCover
                carouselItem = item
                executePendingBindings()
            }
        }
    }
}

private class CarouselDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}