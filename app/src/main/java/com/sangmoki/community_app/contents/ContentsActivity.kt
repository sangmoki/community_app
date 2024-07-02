package com.sangmoki.community_app.contents

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sangmoki.community_app.R
import com.sangmoki.community_app.adapter.ContentsRvAdapter
import com.sangmoki.community_app.model.ContentsModel
import com.sangmoki.community_app.util.FBAuth
import com.sangmoki.community_app.util.FBRef

class ContentsActivity : AppCompatActivity() {

    // DB 참조 객체 선언
    lateinit var myRef: DatabaseReference

    // 어댑터 객체 선언
    lateinit var rvAdapter: ContentsRvAdapter

    // 콘텐츠 id를 가진 북마크 목록 객체 생성
    val bookmarkIdList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contents)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase 데이터베이스 객체 생성
        val database = Firebase.database

        myRef = database.getReference("category").child("all")

        val category = intent.getStringExtra("category")

        // 카테고리별 데이터 가져오기
        if (category == "all") {
            myRef = database.getReference("category")
        } else if (category == "cook") {
            myRef = database.getReference("category").child("cook")
        } else if (category == "economy") {
            myRef = database.getReference("category").child("economy")
        } else if (category == "room") {
            myRef = database.getReference("category").child("room")
        } else if (category == "hobby") {
            myRef = database.getReference("category").child("hobby")
        } else if (category == "interior") {
            myRef = database.getReference("category").child("interior")
        } else if (category == "life") {
            myRef = database.getReference("category").child("life")
        } else {
            myRef = database.getReference("category").child("else")
        }

        // 데이터 삽입
//        myRef.push().setValue(
//            ContentsModel("낚시", "https://i.namu.wiki/i/B8ylYQO0xscUZOXxZgRwuxJp1FsHOcX3gKGQ5dFBfyqGbX3jwX4Kt2ffAsOgJSVzzjr5PB1NJuhQvuuRYR4p_YRU-wyjNz8oLEpS4i5msT56EC78lArhEN-WV8qxXWItFMRLe5zyMuRHfxZlvU-cRg.webp", "https://namu.wiki/w/%EB%82%9A%EC%8B%9C")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("독서", "https://i.namu.wiki/i/UeRMI0-zLQXVNS-oFnrlkT89A9iHJLuAE2l2KVj_8kumEEA1gi6ZXGvkRW6H2V9o8igzRsTy9tcjKV_CG9VOfKc1-hCOuWWu5vx1gNdVVMCBbDrjRpquJxflI14evt4dKQFEnJEQsijgI26GVHb61A.webp", "https://namu.wiki/w/%EC%B1%85")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("여행", "https://images.unsplash.com/photo-1614094082869-cd4e4b2905c7?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Nnx8fGVufDB8fHx8fA%3D%3D", "https://namu.wiki/w/%EC%97%AC%ED%96%89")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("탐험", "https://images.unsplash.com/photo-1554357475-accb8a88a330?w=400&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8fA%3D%3D", "https://namu.wiki/w/%EB%AA%A8%ED%97%98")
//        )

        // 아이템 목록
        val items = ArrayList<ContentsModel>()
        val itemKeyList = ArrayList<String>()
        // 어댑터 연결
        rvAdapter = ContentsRvAdapter(baseContext, items, bookmarkIdList, itemKeyList)

        // realtime database에서 데이터 가져와 items에 담아주기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (category == "all") {
                    // 전체인 경우 모든 카테고리 데이터를 가져온다.
                    for (type in dataSnapshot.children) {
                        for (data in type.children) {
                            val item = data.getValue(ContentsModel::class.java)
                            items.add(item!!)
                            // 순서를 랜덤하게 섞는다.
                            items.shuffle()
                            itemKeyList.add(data.key.toString())
                        }
                    }
                    // all을 제외한 나머지 카테고리
                } else {
                    for (data in dataSnapshot.children) {
                        val item = data.getValue(ContentsModel::class.java)
                        items.add(item!!)
                        itemKeyList.add(data.key.toString())
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(postListener)

        // RecyclerView 레이아웃 객체 생성
        val rv: RecyclerView = findViewById(R.id.rv)

        // 어댑터 연결
        rv.adapter = rvAdapter
        rv.layoutManager = GridLayoutManager(this, 2)

        getBookmarkData()
    }

    // 북마크 데이터 불러오기
    private fun getBookmarkData() {

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 북마크 데이터를 담기 전에 초기화
                bookmarkIdList.clear()

                // 북마크 데이터를 가져와 bookmarkIdList에 저장
                for (data in dataSnapshot.children) {
                    bookmarkIdList.add(data.key.toString())
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)
    }
}