package com.sangmoki.community_app.auth

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sangmoki.community_app.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        val currentUser = auth.currentUser

        // 로그인 버튼 객체 생성
        val loginBtn = findViewById<TextView>(R.id.loginBtn)

        // 로그인 버튼 클릭 이벤트
        loginBtn.setOnClickListener {

            val email = findViewById<TextView>(R.id.emailText).text.toString()
            val password = findViewById<TextView>(R.id.passwordText).text.toString()

            // firebase 로그인 시도 이벤트
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("로그인 시도 =======>>>", "로그인 성공 !")
                        Toast.makeText(
                            baseContext,
                            "로그인 성공!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Log.w("로그인 시도 =======>>>", "로그인 실패 !")
                        Toast.makeText(
                            baseContext,
                            "로그인 실패!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }




    }
}