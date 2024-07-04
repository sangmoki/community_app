package com.sangmoki.community_app.board

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.ActivityBoardWriteBinding
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.util.FBAuth
import com.sangmoki.community_app.util.FBRef
import com.sangmoki.community_app.util.Global
import java.io.ByteArrayOutputStream

class BoardWriteActivity : AppCompatActivity() {

    // 바인딩 객체 선언
    private lateinit var binding: ActivityBoardWriteBinding
    // Firebase realtime DB 참조 객체 선언
    private lateinit var database: DatabaseReference
    // Firebase storage 참조 객체 선언
    private val storage = Firebase.storage
    // 이미지 업로드 여부
    private var isImageUpload = false

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

            // key 값을 미리 만들어 놓고 그 키 값안에 데이터를 저장한다.
            val key = FBRef.boardRef.push().key.toString()
            
            // 게시판 데이터 저장
            FBRef.boardRef
                .child(key)
                .setValue(BoardModel(title, content, FBAuth.getUid(), Global.getTime()))

            // 게시글 작성 완료
            Toast.makeText(this, "게시글 작성 완료", Toast.LENGTH_SHORT).show()

            // 이미지가 업로드 되었으면 업로드 하고 아니라면 실행하지 않는다.
            if (isImageUpload) imageUpload(key)

            // 게시판 목록으로 이동
            finish()
        }

        // + 버튼 클릭 시 이벤트
        binding.imgBtn.setOnClickListener {
            // 이미지 업로드
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
            isImageUpload = true
        }
    }

    // 파일 서버 이미지 업로드 함수
    private fun imageUpload(key: String) {

        val storageRef = storage.reference
        val mountainsRef = storageRef.child(key + ".jpg")

        val imageView = binding.imgBtn

        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // 저장 경로
        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->
        }

    }

    // 이벤트 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // 만약 resultCode가 OK거나 requestCode가 100이면 성공적으로 이루어지면 이미지 영역에 이미지를 설정
        if (resultCode == RESULT_OK && requestCode == 100) {
            binding.imgBtn.setImageURI(data?.data)
        }
    }
}