package com.sangmoki.community_app.contents

import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sangmoki.community_app.R

class ContentsWebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contents_web_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 받아온 url 객체 정의
        val webUrl = intent.getStringExtra("webUrl")
        // 웹뷰 객체 생성
        val webView = findViewById<WebView>(R.id.webView)
        // 웹뷰 설정
        webView.loadUrl(webUrl.toString())
    }
}