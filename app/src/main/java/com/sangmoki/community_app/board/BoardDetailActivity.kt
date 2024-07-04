package com.sangmoki.community_app.board

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityBoardDetailBinding

class BoardDetailActivity : AppCompatActivity() {

    // 바인딩 객체 생성
    private lateinit var binding: ActivityBoardDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 바인딩 레이아웃 설정
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail)
        
        // 전달받은 데이터 객체화
        val title = intent.getStringExtra("title").toString()
        val content = intent.getStringExtra("content").toString()
        val time = intent.getStringExtra("time").toString()

        binding.title.text = title
        binding.content.text = content
        binding.time.text = time

    }
}