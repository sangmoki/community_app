package com.sangmoki.community_app.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// firebase realtime database을 범용적으로 사용하기 위한 클래스 생성
class FBRef {

    // companion object로 선언하여 FBRef 클래스의 인스턴스를 생성하지 않고도 사용 가능하도록 함
    companion object {

        // DB 참조 객체 선언
        lateinit var myRef: DatabaseReference
        private val database = Firebase.database

        val bookmarkRef = database.getReference("bookmark_list")

    }
}