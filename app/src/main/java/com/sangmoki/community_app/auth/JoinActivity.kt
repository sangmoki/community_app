package com.sangmoki.community_app.auth

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    // firebase 인증 객체 생성
    private lateinit var auth: FirebaseAuth
    //
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // firebase 인증 객체 생성
        val currentUser = auth.currentUser
        // 레이아웃 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        // 회원가입 버튼 클릭 이벤트
        binding.joinBtn.setOnClickListener {

            var isValid = true;

            val email = binding.emailText.text.toString()
            val password1 = binding.passwordText1.text.toString()
            val password2 = binding.passwordText2.text.toString()

            // 값이 비어있는지 유효성 검사
            if (email.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "이메일을 입력해주세요.",
                    Toast.LENGTH_SHORT,
                ).show()

                isValid = false
            }

            if (password1.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "비밀번호를 입력해주세요.",
                    Toast.LENGTH_SHORT,
                ).show()

                isValid = false
            }

            if (password2.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "비밀번호를 한번 더 입력해주세요.",
                    Toast.LENGTH_SHORT,
                ).show()

                isValid = false
            }

            if (password1 != password2) {
                Toast.makeText(
                    baseContext,
                    "비밀번호가 일치하지 않습니다.",
                    Toast.LENGTH_SHORT,
                ).show()

                isValid = false
            }

            if (password1.length < 6) {
                Toast.makeText(
                    baseContext,
                    "비밀번호는 6자리 이상이어야 합니다.",
                    Toast.LENGTH_SHORT,
                ).show()

                isValid = false
            }

            if (isValid) {
                // firebase 회원가입 시도 이벤트
                auth.createUserWithEmailAndPassword(email, password1)
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
}