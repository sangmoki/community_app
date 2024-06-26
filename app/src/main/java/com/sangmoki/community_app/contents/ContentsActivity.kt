package com.sangmoki.community_app.contents

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sangmoki.community_app.R

class ContentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contents)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RecyclerView 레이아웃 객체 생성
        val rv: RecyclerView = findViewById(R.id.rv)

        // 아이템 목록
        val items = ArrayList<String>()
        items.add("a")
        items.add("b")
        items.add("c")

        // 어댑터 연결
        val rvAdapter = ContentsRvAdapter(items)
        rv.adapter = rvAdapter

        rv.layoutManager = LinearLayoutManager(this)
    }
}