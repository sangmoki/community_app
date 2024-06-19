package com.sangmoki.community_app.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sangmoki.community_app.MainActivity
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    // 바인딩 라이브러리 객체 생성
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Intro 레이아웃 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        // 로그인 버튼 클릭 이벤트
        binding.loginBtn.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        
        // 회원가입 버튼 클릭 이벤트
        binding.joinBtn.setOnClickListener {
            var intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        
        // 비회원 로그인 버튼 클릭 이벤트
        binding.guestBtn.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        
    }
}