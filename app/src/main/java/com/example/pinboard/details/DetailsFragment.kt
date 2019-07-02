package com.example.pinboard.details

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.pinboard.MainActivity
import com.example.pinboard.PinBoardApplication.Companion.applicationContext
import com.example.pinboard.R
import com.example.pinboard.model.Categories
import com.example.pinboard.model.PinModel
import com.example.pinboard.utils.ImageRequestListener
import com.example.pinboard.utils.Utilities

class DetailsFragment : Fragment(), ImageRequestListener.Callback {

    private val fab by lazy { view!!.findViewById<View>(R.id.fab) }

    private val tvOutput by lazy { view!!.findViewById<TextView>(R.id.tvOutput) }

    private val imageViewPhoto by lazy { view!!.findViewById<ImageView>(R.id.imageViewPhoto) }

    private val imageViewCancel by lazy { view!!.findViewById<ImageView>(R.id.imageViewCancel) }
    private val imageViewReload by lazy { view!!.findViewById<ImageView>(R.id.imageViewReload) }

    private val textViewCategories by lazy { view!!.findViewById<TextView>(R.id.textViewCategories) }
    private val textViewLikes by lazy { view?.findViewById<TextView>(R.id.textViewLikes) }

    private val imageViewAvatar by lazy { view!!.findViewById<ImageView>(R.id.imageViewAvatar) }
    private val textViewAvatar by lazy { view?.findViewById<TextView>(R.id.textViewAvatar) }

    private lateinit var constraintDetails: ConstraintLayout

    private var pinModel: PinModel? = null

    override fun onFailure(message: String?) {
        Toast.makeText(context, getString(R.string.failed_load, message), Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(dataSource: String) {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinModel = MainActivity.SELECTED_PIN
        if (pinModel == null) {
            tvOutput.setText(getString(R.string.pin_no_data))
        } else {
            val color = Color.parseColor(pinModel?.color)
            val dark: Boolean = Utilities().isDark(color)

            fab.setOnClickListener { result ->
                Utilities().openWebPage(pinModel?.links?.html.toString(), applicationContext())
            }

            constraintDetails = view.findViewById(R.id.constraintDetails)
            constraintDetails.setBackgroundColor(color)

            Glide.with(this)
                .load(pinModel?.urls?.small)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(
                    ImageRequestListener(
                        this
                    )
                )
                .apply(
                    RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.nocover)
                        .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.DATA)
                )
                .into(imageViewPhoto)

            imageViewPhoto.setOnClickListener({ result ->
                Utilities().openWebPage(pinModel?.urls?.full.toString(), applicationContext())
            })


            imageViewCancel.setOnClickListener({ result ->
                Toast.makeText(context, "Image pause request", Toast.LENGTH_LONG).show()
                Glide.with(this).pauseRequests()
            })
            imageViewReload.setOnClickListener({ result ->
                Toast.makeText(context, "Image resume request", Toast.LENGTH_LONG).show()
                Glide.with(this).resumeRequests()
            })


            val it: ListIterator<Categories>? = pinModel?.categories?.listIterator()
            if (listOf(it).count() > 0) {
                val stringBuilder = StringBuilder()
                stringBuilder.append(getString(R.string.category) + "\r\n")
                while (it!!.hasNext()) {
                    val item = it.next()
                    stringBuilder.append(item.title + " (" + item.photo_count + ")\r\n")
                }
                textViewCategories.setText(stringBuilder.toString())
                if (dark) textViewCategories?.setTextColor(Utilities().getResColor(applicationContext()))
            }

            textViewLikes?.setText(getString(R.string.likes, pinModel?.likes?.toString()))
            if (dark) textViewLikes?.setTextColor(Utilities().getResColor(applicationContext()))

            Glide.with(this)
                .load(pinModel?.user?.profile_image?.small)
                .apply(
                    RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.profile)
                        .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.DATA)
                )
                .into(imageViewAvatar)

            textViewAvatar?.setText(pinModel?.user?.name)

            imageViewAvatar.setOnClickListener({ result ->
                Utilities().openWebPage(pinModel?.user?.links?.html.toString(), applicationContext())
            })
            if (dark) textViewAvatar?.setTextColor(Utilities().getResColor(applicationContext()))

            textViewAvatar?.setOnClickListener({ result ->
                Utilities().openWebPage(pinModel?.user?.links?.html.toString(), applicationContext())
            })
        }
    }

}