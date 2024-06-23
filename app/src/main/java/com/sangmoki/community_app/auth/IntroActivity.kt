package com.sangmoki.community_app.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sangmoki.community_app.MainActivity
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    // 바인딩 라이브러리 객체 생성
    private lateinit var binding: ActivityIntroBinding
    // firebase 인증 객체 생성
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // firebase 인증 객체 생성
        auth = Firebase.auth
        val currentUser = auth.currentUser
        
        // Intro 레이아웃 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        // 로그인 버튼 클릭 이벤트
        binding.loginActivityBtn.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        
        // 회원가입 버튼 클릭 이벤트
        binding.joinActivityBtn.setOnClickListener {
            var intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        
        // 비회원 로그인 버튼 클릭 이벤트
        binding.guestActivityBtn.setOnClickListener {
            // 익명 로그인 시도
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        // 익명 로그인 시도 성공 시 스택 제거 후 메인 액티비티로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        Log.d("익명 로그인 시도 ======>>", "익명 로그인 성공 !")
                        Toast.makeText(
                            baseContext,
                            "익명 로그인 성공 !",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Log.d("익명 로그인 시도 ======>>", "익명 로그인 실패 !")
                        Toast.makeText(
                            baseContext,
                            "익명 로그인 실패 !",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
        
    }
}