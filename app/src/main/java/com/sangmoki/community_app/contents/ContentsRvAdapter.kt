package com.sangmoki.community_app.contents

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sangmoki.community_app.R

class ContentsRvAdapter(val items: ArrayList<String>) : RecyclerView.Adapter<ContentsRvAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRvAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentsRvAdapter.ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: String) {

        }
    }
}