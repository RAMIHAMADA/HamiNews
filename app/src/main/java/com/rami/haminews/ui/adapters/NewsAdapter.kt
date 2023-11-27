package com.rami.haminews.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rami.haminews.databinding.ItemArticleBinding
import com.rami.haminews.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]

        with(holder.binding) {
            root.tag = article
            Glide.with(holder.itemView.context).load(article.urlToImage).into(articleImage)
            articleImage.clipToOutline = true
            articleTitle.text = article.title
            articleDate.text = article.publishedAt
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(article)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}
