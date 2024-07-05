package com.sangmoki.community_app.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sangmoki.community_app.R
import com.sangmoki.community_app.adapter.CommentLvAdapter
import com.sangmoki.community_app.databinding.ActivityBoardDetailBinding
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.model.CommentModel
import com.sangmoki.community_app.util.FBAuth
import com.sangmoki.community_app.util.FBRef
import com.sangmoki.community_app.util.Global

class BoardDetailActivity : AppCompatActivity() {

    // 바인딩 객체 생성
    private lateinit var binding: ActivityBoardDetailBinding

    // 댓글 목록 객체 생성
    private val commentList = mutableListOf<CommentModel>()

    private lateinit var commentAdapter : CommentLvAdapter

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

        // 게시글 수정 버튼 클릭 이벤트
        binding.settingBtn.setOnClickListener {
            // 삭제 수정 다이얼로그 띄우기
            showFixDialog(key)
        }

        // 데이터 조회 함수 호출
        getBoardDetailData(key)
        // 이미지 조회 함수 호출
        getImageData(key)
        // 댓글 조회 함수 호출
        getCommentData(key)

        // 댓글 작성 버튼 클릭 이벤트
        binding.commentBtn.setOnClickListener {
            insertComment(key)
        }

        // 댓글 어댑터 연결
        commentAdapter = CommentLvAdapter(commentList)
        binding.commentListView.adapter = commentAdapter
    }
    
    // 댓글 작성 함수
    private fun insertComment(key: String) {

        val comment = binding.comment.text.toString()

        // board key 값 하위에 commentkey 값으로 댓글 하나씩 생성
        FBRef.commentRef
            .child(key)
            .push()
            .setValue(CommentModel(comment, Global.getTime()))

        Toast.makeText(this, "댓글이 작성되었습니다.", Toast.LENGTH_SHORT).show()
        binding.comment.setText("")
    }

    // 댓글 조회 함수
    private fun getCommentData(key: String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터가 변경될 때 초기화 -> 새로 동기화 시킨다.
                commentList.clear()

                for (data in dataSnapshot.children) {
                    val item = data.getValue(CommentModel::class.java)
                    commentList.add(item!!)
                }
                commentList.reverse()
                // 어댑터 갱신
                commentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        FBRef.commentRef.child(key).addValueEventListener(postListener)
    }

    // 다이얼로그 띄우는 함수
    private fun showFixDialog(key: String) {
        val mDialogView = layoutInflater.inflate(R.layout.custom_dialog, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        mBuilder.show()

        // 수정 버튼 클릭 이벤트
        mDialogView.findViewById<Button>(R.id.fixBtn)?.setOnClickListener {

            Toast.makeText(this, "게시글 수정 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BoardEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)

            finish()
        }

        // 삭제 버튼 클릭 이벤트
        mDialogView.findViewById<Button>(R.id.deleteBtn)?.setOnClickListener {
            FBRef.boardRef.child(key).removeValue()
            Toast.makeText(this, "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

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

                val myUid = FBAuth.getUid()
                val writerUid = data?.uid

                // 작성자와 동일한 UID인 경우 게시판 수정 가능하게 Visible 설정
                if (myUid.equals(writerUid)) {
                    binding.settingBtn.isVisible = true
                } else {
                    binding.settingBtn.isVisible = false
                }
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
            } else {
                // 이미지가 없으면 이미지 영역 안보이게 설정
                binding.image.isVisible = false
            }
        }
    }
}