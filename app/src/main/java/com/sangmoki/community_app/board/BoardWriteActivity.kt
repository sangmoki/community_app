package com.sangmoki.community_app.board

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityBoardWriteBinding
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.util.FBAuth
import com.sangmoki.community_app.util.FBRef
import com.sangmoki.community_app.util.Global

class BoardWriteActivity : AppCompatActivity() {

    // 바인딩 객체 선언
    private lateinit var binding: ActivityBoardWriteBinding
    // Firebase realtime DB 참조 객체 선언
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 액티비티 레이아웃 설정
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_write)

        // DB 참조 객체 생성
        database = Firebase.database.reference

        // 작성 버튼 클릭 이벤트
        binding.writeBtn.setOnClickListener {
            val title = binding.writeTitle.text.toString()
            val content = binding.writeContent.text.toString()
            
            // 게시판 데이터 저장
            FBRef.boardRef
                .push()
                .setValue(BoardModel(title, content, FBAuth.getUid(), Global.getTime()))

        }

    }
}