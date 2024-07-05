package com.sangmoki.community_app.board

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityBoardEditBinding
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.util.FBRef
import com.sangmoki.community_app.util.Global

class BoardEditActivity : AppCompatActivity() {

    // 바인딩 변수 선언
    private lateinit var binding: ActivityBoardEditBinding

    private lateinit var writerUid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_board_edit)
        
        // 넘겨받은 key 값 받기
        var key = intent.getStringExtra("key").toString()

        // 데이터 조회 함수 호출
        getBoardData(key)
        // 이미지 조회 함수 호출
        getImageData(key)

        // 수정 버튼 클릭 이벤트
        binding.editBtn.setOnClickListener {
            editBoardData(key)
        }
    }

    // 상세 데이터 수정 함수 -> 기존 데이터에서 수정 데이터로 덮어씌운다.
    private fun editBoardData(key : String) {
        FBRef.boardRef
            .child(key)
            .setValue(BoardModel(
                binding.title.text.toString(),
                binding.content.text.toString(),
                writerUid,
                Global.getTime())
            )

        Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    // 상세 데이터 조회 함수
    private fun getBoardData(key: String) {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터 객체화
                val data = dataSnapshot.getValue(BoardModel::class.java)

                // 데이터 바인딩
                binding.title.setText(data?.title)
                binding.content.setText(data?.content)
                writerUid = data!!.uid

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FBRef.boardRef.child(key).addValueEventListener(postListener)
    }

    // 이미지 데이터 조회 함수
    private fun getImageData(key: String) {
        val storageRef = Firebase.storage.reference.child(key + ".jpg")
        val image = binding.image

        storageRef.downloadUrl.addOnCompleteListener { tast ->
            if (tast.isSuccessful) {
                Glide.with(this)
                    .load(tast.result)
                    .into(image)
            }
        }
    }
}