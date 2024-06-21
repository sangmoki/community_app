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
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // firebase 인증 객체 생성
    private lateinit var auth: FirebaseAuth

    // bidning 객체 생성
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        // 로그인 버튼 클릭 이벤트
        binding.loginBtn.setOnClickListener {

            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

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