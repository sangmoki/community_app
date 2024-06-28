package com.sangmoki.community_app.contents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangmoki.community_app.R

class ContentsRvAdapter(val context: Context, val items: ArrayList<ContentsModel>) : RecyclerView.Adapter<ContentsRvAdapter.ViewHolder>() {

    // 클릭 이벤트 인터페이스 정의
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRvAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentsRvAdapter.ViewHolder, position: Int) {

        // 아이템 클릭 이벤트 정의
        if(itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick?.onClick(v, position)
            }
        }

        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ContentsModel) {
            val title = itemView.findViewById<TextView>(R.id.title)
            val imgUrl = itemView.findViewById<ImageView>(R.id.imageUrl)

            // title에 item의 title 맵핑해준다.
            title.text = item.title

            // 글라이드 라이브러리를 통해
            Glide.with(context)
                // item의 imgUrl에
                .load(item.imgUrl)
                // imgUrl에 이미지를 넣어준다.
                .into(imgUrl)

        }
    }
}