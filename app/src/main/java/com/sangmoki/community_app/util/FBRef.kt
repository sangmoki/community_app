package com.sangmoki.community_app.util

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// firebase realtime database을 범용적으로 사용하기 위한 클래스 생성
class FBRef {

    // companion object로 선언하여 FBRef 클래스의 인스턴스를 생성하지 않고도 사용 가능하도록 함
    companion object {

        // DB 참조 객체 선언
        private val database = Firebase.database

        // 모든 카테고리 데이터를 가져오기 위한 참조 객체 선언
        val categoryAll = database.getReference("category")

        // 북마크 데이터를 가져오기 위한 참조 객체 선언
        val bookmarkRef = database.getReference("bookmark_list")

        // 게시판 데이터를 가져오기 위한 참조 객체 선언
        val boardRef = database.getReference("board")

    }
}