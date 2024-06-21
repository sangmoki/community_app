package com.sangmoki.community_app.auth

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.sangmoki.community_app.R

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_join)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // firebase 인증 객체 생성
        val currentUser = auth.currentUser

        // 회원가입 버튼 객체 생성
        val joinBtn = findViewById<TextView>(R.id.joinBtn)

        // 회원가입 버튼 클릭 이벤트
        joinBtn.setOnClickListener {
            
            val email = findViewById<TextView>(R.id.emailText).text.toString()
            val password = findViewById<TextView>(R.id.passwordText).text.toString()
            
            // firebase 회원가입 시도 이벤트
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("회원가입 시도 =======>", "회원가입 성공 !")
                        Toast.makeText(
                            baseContext,
                            "회원가입 성공 !",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Log.w("회원가입 시도 =======>", "회원가입 실패 !")
                        Toast.makeText(
                            baseContext,
                            "회원가입 실패 !",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}