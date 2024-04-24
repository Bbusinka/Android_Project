package com.example.galeria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GalleryPagerAdapter(private val context: Context, private val images: List<Image>) :
    RecyclerView.Adapter<GalleryPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun getTitleResId(position: Int): Int {
        return R.string.tab_title_gallery
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        fun bind(image: Image) {
            Glide.with(context).load(image.uri).into(imageView)
            ratingBar.rating = image.rating.toFloat()
        }
    }
}