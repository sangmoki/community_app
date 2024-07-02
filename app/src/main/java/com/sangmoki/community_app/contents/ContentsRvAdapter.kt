package com.sangmoki.community_app.contents

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sangmoki.community_app.R
import com.sangmoki.community_app.model.BookmarkModel
import com.sangmoki.community_app.model.ContentsModel
import com.sangmoki.community_app.util.FBAuth
import com.sangmoki.community_app.util.FBRef

class ContentsRvAdapter(val context: Context,
                        val items: ArrayList<ContentsModel>,
                        val bookmarkIdList: MutableList<String>,
                        val itemKeyList: ArrayList<String>): RecyclerView.Adapter<ContentsRvAdapter.ViewHolder>() {

    // 클릭 이벤트 인터페이스 정의 (기존 방식)
//    interface ItemClick {
//        fun onClick(view: View, position: Int)
//    }

//    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentsRvAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.contents_rv_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ContentsRvAdapter.ViewHolder, position: Int) {

        // 아이템 클릭 이벤트 정의 (기존 방식)
//        if(itemClick != null) {
//            holder.itemView.setOnClickListener { v ->
//                itemClick?.onClick(v, position)
//            }
//        }

        holder.bindItems(items[position], itemKeyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ContentsModel, itemKey: String) {

            // itemView 클릭 이벤트 정의
            itemView.setOnClickListener {
                itemView.context
                    .startActivity(Intent(context, ContentsWebViewActivity::class.java)
                    .putExtra("webUrl", item.webUrl))
            }

            val title = itemView.findViewById<TextView>(R.id.title)
            val imgUrl = itemView.findViewById<ImageView>(R.id.imageUrl)
            val bookmark = itemView.findViewById<ImageView>(R.id.bookmark)

            // title에 item의 title 맵핑해준다.
            title.text = item.title

            // 북마크 아이콘 색상 변경
            if (bookmarkIdList.contains(itemKey)) {
                bookmark.setImageResource(R.drawable.bookmark_color)
            } else {
                bookmark.setImageResource(R.drawable.bookmark_white)
            }

            // 북마크 클릭 이벤트 정의
            bookmark.setOnClickListener {

                if (bookmarkIdList.contains(itemKey)) {

                    // 북마크가 이미 true 일 때 북마크를 삭제한다.
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(itemKey)
                        .removeValue()
                } else {
                    // 북마크를 클릭하면 uid 하위에 itemKey를 저장한다. 그럼 각 uid 별로 북마크 데이터가 저장된다.
                    FBRef.bookmarkRef
                        .child(FBAuth.getUid())
                        .child(itemKey)
                        .setValue(BookmarkModel(true))
                }

                Toast.makeText(
                    context,
                    bookmark.toString(),
                    Toast.LENGTH_SHORT,
                ).show()
            }

            // 글라이드 라이브러리를 통해
            Glide.with(context)
                // item의 imgUrl에
                .load(item.imgUrl)
                // imgUrl에 이미지를 넣어준다.
                .into(imgUrl)

        }
    }
}