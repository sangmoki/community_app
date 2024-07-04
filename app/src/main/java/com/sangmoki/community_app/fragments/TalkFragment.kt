package com.sangmoki.community_app.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sangmoki.community_app.R
import com.sangmoki.community_app.adapter.BoardLvAdapter
import com.sangmoki.community_app.board.BoardDetailActivity
import com.sangmoki.community_app.board.BoardWriteActivity
import com.sangmoki.community_app.databinding.FragmentTalkBinding
import com.sangmoki.community_app.model.BoardModel
import com.sangmoki.community_app.util.FBRef

class TalkFragment : Fragment() {

    // 바인딩 변수 선언
    private lateinit var binding: FragmentTalkBinding

    // 게시판 ListView Adapter 객체 생성
    private lateinit var boardLvAdapter: BoardLvAdapter

    // 게시판 목록 변수 생성
    private val boardList = mutableListOf<BoardModel>()

    // 게시판 key 값 목록 변수 생성
    private val boardKeyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // DataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        boardLvAdapter = BoardLvAdapter(boardList)
        binding.boardListView.adapter = boardLvAdapter

        // 작성 버튼 클릭 시 게시글 작성 페이지로 이동
        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }

        // 1. 액티비티 데이터 전달 및 이동 방법 - listView에 있는 데이터의 각각 객체 값 하나하나 액티비티로 전달하는 방법
//        binding.boardListView.setOnItemClickListener { parent, view, position, id ->
//            val intent = Intent(context, BoardDetailActivity::class.java)
//            intent.putExtra("title", boardList[position].title)
//            intent.putExtra("content", boardList[position].content)
//            intent.putExtra("time", boardList[position].time)
//            startActivity(intent)
//        }

        // 2. 액티비티 데이터 전달 및 이동 방법 - boardList[position]에 해당하는 데이터 key id 기반으로 데이터를 불러오는 방법
        binding.boardListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(context, BoardDetailActivity::class.java)
            // key 값 전달
            intent.putExtra("key", boardKeyList[position])
            startActivity(intent)
        }

        // Home 버튼 클릭 이벤트
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        // Tip 버튼 클릭 이벤트
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        // Bookmark 버튼 클릭 이벤트
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        // Store 버튼 클릭 이벤트
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }

        getFBBoardData()

        return binding.root
    }

    // 게시판 데이터 가져오는 함수
    private fun getFBBoardData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // 데이터가 변경될 때 초기화 -> 새로 동기화 시킨다.
                boardList.clear()

                for (data in dataSnapshot.children) {

                    boardKeyList.add(data.key.toString())
                    boardList.add(data.getValue(BoardModel::class.java)!!)
                }
                // 데이터 순서 변경 -> 마지막 입력한 데이터가 맨 위로
                boardKeyList.reverse()
                boardList.reverse()
                // 데이터 불러온 후 어댑터 동기화
                boardLvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        // 게시판 데이터 가져오기
        FBRef.boardRef.addValueEventListener(postListener)
    }
}