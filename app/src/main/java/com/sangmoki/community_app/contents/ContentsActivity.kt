package com.sangmoki.community_app.contents

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.sangmoki.community_app.R
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

        myRef = database.getReference("contents")

//        val category = intent.getStringExtra("category")

        // 카테고리별 데이터 가져오기
//        if (category == "all") {
//            myRef = database.getReference("all")
//        } else if (category == "cook") {
//            myRef = database.getReference("cook")
//        } else if (category == "economy") {
//            myRef = database.getReference("economy")
//        } else if (category == "room") {
//            myRef = database.getReference("room")
//        } else if (category == "hobby") {
//            myRef = database.getReference("hobby")
//        } else if (category == "interior") {
//            myRef = database.getReference("interior")-
//        } else if (category == "life") {
//            myRef = database.getReference("life")
//        } else {
//            myRef = database.getReference("else")
//        }

        // 데이터 삽입
//        myRef.push().setValue(
//            ContentsModel("밥솥 리코타치즈 황금레시피", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FblYPPY%2Fbtq66v0S4wu%2FRmuhpkXUO4FOcrlOmVG4G1%2Fimg.png", "https://philosopher-chan.tistory.com/1235?category=941578")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("황금노른자장 황금레시피", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FznKK4%2Fbtq665AUWem%2FRUawPn5Wwb4cQ8BetEwN40%2Fimg.png", "https://philosopher-chan.tistory.com/1236?category=941578")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("사골곰탕 파스타 황금레시피", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fbtig9C%2Fbtq65UGxyWI%2FPRBIGUKJ4rjMkI7KTGrxtK%2Fimg.png", "https://philosopher-chan.tistory.com/1237?category=941578")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("아웃백 투움바 파스타 황금레시피", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcOYyBM%2Fbtq67Or43WW%2F17lZ3tKajnNwGPSCLtfnE1%2Fimg.png", "https://philosopher-chan.tistory.com/1238?category=941578")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("최애 당면 찜닭 황금레시피", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fekn5wI%2Fbtq66UlN4bC%2F8NEzlyot7HT4PcjbdYAINk%2Fimg.png", "https://philosopher-chan.tistory.com/1239?category=941578")
//        )
//
//        myRef.push().setValue(
//            ContentsModel("스팸 부대 국수 황금레시피", "https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F123LP%2Fbtq65qy4hAd%2F6dgpC13wgrdsnHigepoVT1%2Fimg.png", "https://philosopher-chan.tistory.com/1240?category=941578")
//        )



        // 아이템 목록
        val items = ArrayList<ContentsModel>()
        val itemKeyList = ArrayList<String>()
        // 어댑터 연결
        rvAdapter = ContentsRvAdapter(baseContext, items, bookmarkIdList, itemKeyList)

        // realtime database에서 데이터 가져와 items에 담아주기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    val item = data.getValue(ContentsModel::class.java)
                    items.add(item!!)
                    itemKeyList.add(data.key.toString())
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