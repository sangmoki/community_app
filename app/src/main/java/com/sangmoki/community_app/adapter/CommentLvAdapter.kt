package com.sangmoki.community_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.sangmoki.community_app.R
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.model.CommentModel

class CommentLvAdapter (val commentList: MutableList<CommentModel>) : BaseAdapter() {
    override fun getCount(): Int {

        return commentList.size
    }

    override fun getItem(position: Int): Any {

        return commentList[position]
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_item, parent, false)
        }

        return convertView!!
    }
}