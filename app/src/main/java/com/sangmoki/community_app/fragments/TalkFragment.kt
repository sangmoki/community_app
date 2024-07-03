package com.sangmoki.community_app.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sangmoki.community_app.R
import com.sangmoki.community_app.adapter.BoardLvAdapter
import com.sangmoki.community_app.board.BoardWriteActivity
import com.sangmoki.community_app.databinding.FragmentTalkBinding
import com.sangmoki.community_app.model.BoardModel

class TalkFragment : Fragment() {

    // 바인딩 변수 선언
    private lateinit var binding: FragmentTalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // DataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk, container, false)

        val boardList = mutableListOf<BoardModel>()
        boardList.add(BoardModel("제목1", "내용1", "작성자1", "2021-09-01"))

        val adapter = BoardLvAdapter(boardList)
        binding.boardListView.adapter = adapter

        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
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

        return binding.root
    }
}