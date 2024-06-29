package com.sangmoki.community_app.util

import com.google.firebase.auth.FirebaseAuth

// 범용적으로 사용하기 위해 FirebaseAuth 클래스 생성
class FBAuth {
    companion object {
        // Firebase 인증 객체
        private lateinit var auth: FirebaseAuth

        // 현재 로그인 유저의 uid 값 반환
        fun getUid(): String {
            auth = FirebaseAuth.getInstance()
            return auth.currentUser?.uid.toString()
        }
    }
}