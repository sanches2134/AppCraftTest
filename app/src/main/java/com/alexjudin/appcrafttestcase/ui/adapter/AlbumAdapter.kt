package com.alexjudin.appcrafttestcase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexjudin.appcrafttestcase.R
import com.alexjudin.appcrafttestcase.domain.entity.AlbumModel
import kotlinx.android.synthetic.main.item_preview.view.*

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<AlbumModel>() {

        override fun areItemsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumModel, newItem: AlbumModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_preview,
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
            tvTitle.text = article.title

            setOnClickListener {
                onItemClickListener?.let { it(article)
                }
            }
        }
    }

    private var onItemClickListener: ((AlbumModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (AlbumModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}