package com.schaefer.mymovies.presentation.adapters.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.schaefer.mymovies.presentation.model.Show
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_card_image.*

class HomeListItemViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    fun bind(show: Show, itemClickListener: OnItemClickListener) {
        tvItemShowName.text = show.name
        tvItemRate.text = show.rating?.average.toString()

        Glide.with(itemView).load(show.image?.original).centerCrop().into(ivItemPoster)

        itemView.setOnClickListener { itemClickListener.onItemClick(show) }
    }

    override val containerView: View
        get() = itemView
}