package com.alexjudin.appcrafttestcase.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexjudin.appcrafttestcase.R
import com.alexjudin.appcrafttestcase.domain.entity.PhotosModelItem
import com.github.chrisbanes.photoview.PhotoView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_album.view.*


class PhotosAdapter(private val context: Context) : RecyclerView.Adapter<PhotosAdapter.ArticleViewHolder>() {




    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<PhotosModelItem>() {
        override fun areItemsTheSame(oldItem: PhotosModelItem, newItem: PhotosModelItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotosModelItem, newItem: PhotosModelItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {


        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_album,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {
        val article = differ.currentList[position]

        holder.itemView.apply {
            try {
                Picasso.get().load(article.thumbnailUrl).placeholder(R.drawable.ic_album)
                    .into(ivArticleImage)
            } catch (e: Exception) {
                ivArticleImage.setImageResource(R.drawable.ic_album)
            }

            ivArticleImage.setOnClickListener {
                showDetailPhoto(article)
            }
            title_TV.text = article.title

        }

    }

    private fun showDetailPhoto(photosModelItem: PhotosModelItem){
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.detail_photo, null)
        try {
            Picasso.get().load(photosModelItem.url).placeholder(R.drawable.ic_album)
                .into( view.findViewById<PhotoView>(R.id.big_photo))
        } catch (e: Exception) {
            view.findViewById<PhotoView>(R.id.big_photo).setImageResource(R.drawable.ic_album)
        }
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

    }




    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}