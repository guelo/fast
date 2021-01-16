package com.miguel.fastassignment

import android.graphics.drawable.Icon
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.bumptech.glide.Glide
import com.miguel.fastassignment.data.Movie
import com.miguel.fastassignment.databinding.RowBinding

internal class MoviesAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<ViewHolder>() {

    private val movies = mutableListOf<Movie>()
    val selected = mutableSetOf<Movie>()

    fun reset() {
        movies.clear()
        selected.clear()
        selectedListener?.invoke(selected)
    }

    fun replace(newList: List<Movie>) {
        movies.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowBinding.inflate(layoutInflater, parent, false)
        val vh = ViewHolder(binding)
        binding.card.setOnClickListener {
            binding.card.isChecked = !binding.card.isChecked

            val position = vh.adapterPosition
            if (position != NO_POSITION) {
                val movie = movies[position]
                if (binding.card.isChecked) {
                    selected.add(movie)
                } else {
                    selected.remove(movie)
                }
                selectedListener?.invoke(selected)
            }
        }
        return vh
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        val selected = selected.contains(movie)
        holder.bind(movie, selected)
    }

    var selectedListener: ((Set<Movie>) -> Unit)? = null

}

internal class ViewHolder(
    private val binding: RowBinding
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.card.setOnClickListener {
            binding.card.isChecked = !binding.card.isChecked
        }
    }

    fun bind(movie: Movie, selected: Boolean) {
        Glide.with(itemView.context)
            .load(movie.imageUrl)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.imageView);

        binding.title.text = movie.title
        binding.year.text = movie.year
        val drawable = when (movie.type) {
            Movie.Type.MOVIE -> R.drawable.ic_baseline_movie_24
            Movie.Type.SERIES -> R.drawable.ic_baseline_subscriptions_24
            Movie.Type.EPISODE -> R.drawable.ic_baseline_tv_24
            Movie.Type.GAME -> R.drawable.ic_baseline_videogame_asset_24
        }
        binding.type.setImageIcon(Icon.createWithResource(itemView.context, drawable))

        binding.card.isChecked = selected
    }
}