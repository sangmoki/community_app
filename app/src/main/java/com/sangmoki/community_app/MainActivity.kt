package com.sangmoki.community_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sangmoki.community_app.auth.LogoutActivity

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        val currentUser = auth.currentUser

        // 메뉴 버튼 클릭 이벤트
        findViewById<ImageView>(R.id.menuBtn).setOnClickListener {
            // 로그아웃 액티비티로 이동
            val intent = Intent(this, LogoutActivity::class.java)
            startActivity(intent)
        }

        // 로그아웃 버튼 클릭 이벤트
        // findViewById<Button>(R.id.logoutBtn).setOnClickListener {
            // 로그아웃 이벤트
        //    auth.signOut()

            // 스택 제거 후 인트로 화면으로 이동
        //    val intent = Intent(this, IntroActivity::class.java)
        //    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        //    startActivity(intent)
        // }
    }
}