package com.sangmoki.community_app.model

data class BoardModel (

    var title: String,    // 게시글 제목
    var content: String,  // 게시글 내용
    var uid: String,      // 게시글 작성자 uid
    var time: String      // 게시글 작성 시간
)