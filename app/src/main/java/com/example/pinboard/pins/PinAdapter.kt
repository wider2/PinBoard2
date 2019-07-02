package com.example.pinboard.pins

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.pinboard.R
import com.example.pinboard.model.PinModel
import com.example.pinboard.utils.Utilities
import kotlinx.android.synthetic.main.card_layout.view.*

class PinAdapter(
    private var pinList: List<PinModel>,
    private var onDataListener: OnDataListener
) : RecyclerView.Adapter<PinAdapter.PinHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PinHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
    )

    override fun onBindViewHolder(holder: PinHolder, position: Int) =
        holder.bind(pinList[position], onDataListener)

    override fun getItemCount() = pinList.size


    class PinHolder(articleView: View) : RecyclerView.ViewHolder(articleView) {

        fun bind(pinModel: PinModel, onDataListener: OnDataListener) = with(itemView) {

            val color = Color.parseColor(pinModel.color)
            card_view.setBackgroundColor(color)

            Glide.with(this)
                .load(pinModel.urls.thumb)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(
                    RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.nocover)
                        .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.DATA)
                )
                .into(imageViewCard)

            imageViewCard.setOnClickListener({ _ -> onDataListener.profileClicked(pinModel) })

            Glide.with(this)
                .load(pinModel.user.profile_image.small)
                .apply(
                    RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.profile)
                        .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.DATA)
                )
                .into(imageViewAvatar)


            with(pinModel) {
                textViewAvatar.text = user.name

                imageViewAvatar.setOnClickListener({ _ ->
                    Utilities().openWebPage(user.links.html, context)
                })

                val dark: Boolean = Utilities().isDark(color)
                if (dark) textViewAvatar.setTextColor(Utilities().getResColor(context))

                textViewAvatar.setOnClickListener({ _ ->
                    Utilities().openWebPage(user.links.html, context)
                })
            }
        }

    }
}
