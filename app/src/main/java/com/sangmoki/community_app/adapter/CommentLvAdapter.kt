package com.sangmoki.community_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import com.sangmoki.community_app.R
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.model.CommentModel
import org.w3c.dom.Text

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

        val comment = convertView?.findViewById<TextView>(R.id.comment)
        val time = convertView?.findViewById<TextView>(R.id.time)

        comment?.setText(commentList[position].comment)
        time?.setText(commentList[position].time)

        return convertView!!
    }
}