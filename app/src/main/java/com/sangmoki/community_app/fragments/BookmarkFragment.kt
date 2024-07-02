package com.sangmoki.community_app.fragments

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
import com.sangmoki.community_app.databinding.FragmentBookmarkBinding
import com.sangmoki.community_app.databinding.FragmentStoreBinding
import com.sangmoki.community_app.model.ContentsModel
import com.sangmoki.community_app.util.FBRef


class BookmarkFragment : Fragment() {

    // 바인딩 변수 선언
    private lateinit var binding: FragmentBookmarkBinding
    // 로그 찍기 위한 TAG 선언
    private val TAG = BookmarkFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 전체 카테고리에 있는 컨텐츠 데이터 다 가져오기
        // realtime database에서 데이터 가져와 items에 담아주기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    Log.d(TAG, data.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBRef.categoryAll.addValueEventListener(postListener)


        // 사용자가 북마크한 정보를 다 가져온다.

        // 전체 컨텐츠 중에서 사용자가 북마크한 정보만 보여준다.

        // DataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)

        // Home 버튼 클릭 이벤트
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_homeFragment)
        }

        // Tip 버튼 클릭 이벤트
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_tipFragment)
        }

        // Talk 버튼 클릭 이벤트
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_talkFragment)
        }

        // Store 버튼 클릭 이벤트
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_bookmarkFragment_to_storeFragment)
        }

        return binding.root
    }
}