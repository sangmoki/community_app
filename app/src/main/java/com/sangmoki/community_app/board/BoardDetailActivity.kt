package com.sangmoki.community_app.board

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityBoardDetailBinding
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.util.FBRef

class BoardDetailActivity : AppCompatActivity() {

    // 바인딩 객체 생성
    private lateinit var binding: ActivityBoardDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 바인딩 레이아웃 설정
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_detail)

        // 1번째 방법 - 데이터를 하나하나 넘겨받아 화면에 표시
        // 전달받은 데이터 객체화
//        val title = intent.getStringExtra("title").toString()
//        val content = intent.getStringExtra("content").toString()
//        val time = intent.getStringExtra("time").toString()
//
//        binding.title.text = title
//        binding.content.text = content
//        binding.time.text = time
        
        
        // 2번째 방법 - key 값을 받아 key값으로 데이터 조회
        val key = intent.getStringExtra("key").toString()
        // 데이터 조회 함수 호출
        getBoardDetailData(key)

    }

    // 상세 데이터 조회 함수
    private fun getBoardDetailData(key: String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터 객체화
                val data = dataSnapshot.getValue(BoardModel::class.java)

                // 데이터 바인딩
                binding.title.text = data?.title
                binding.content.text = data?.content
                binding.time.text = data?.time

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }
}