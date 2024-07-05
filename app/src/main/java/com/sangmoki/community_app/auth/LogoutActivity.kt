package com.sangmoki.community_app.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.sangmoki.community_app.R

class LogoutActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        // 로그아웃 버튼 객체 생성
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        // 로그아웃 버튼 클릭 이벤트
        logoutBtn.setOnClickListener {

            // 로그아웃 처리
            Firebase.auth.signOut()
            // 로그아웃 처리 알림
            Toast.makeText(
                baseContext,
                "로그아웃 되었습니다.",
                Toast.LENGTH_SHORT,
            ).show()
            
            // 로그아웃이 완료 된 후 로그인 화면으로 이동
             val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
             startActivity(intent)
             finish()
        }
    }
}