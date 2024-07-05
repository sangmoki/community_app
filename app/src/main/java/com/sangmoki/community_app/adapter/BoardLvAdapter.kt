package com.sangmoki.community_app.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.sangmoki.community_app.R
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.util.FBAuth

class BoardLvAdapter(val boardList: MutableList<BoardModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(position: Int): Any {
        return boardList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView
        if (convertView == null) {
               convertView = LayoutInflater.from(parent?.context).inflate(R.layout.board_list_item, parent, false)
        }

        // 레이아웃 객체 생성
        val itemView = convertView?.findViewById<LinearLayout>(R.id.itemView)
        val title = convertView?.findViewById<TextView>(R.id.title)
        val content = convertView?.findViewById<TextView>(R.id.content)
        val time = convertView?.findViewById<TextView>(R.id.time)

        // 내가 쓴 글 배경 색깔 변경
        if (boardList[position].uid.equals(FBAuth.getUid())) {
            itemView?.setBackgroundColor(Color.parseColor("#ffa500"))
        }

        title?.text = boardList[position].title
        content?.text = boardList[position].content
        time?.text = boardList[position].time

        return convertView!!
    }
}