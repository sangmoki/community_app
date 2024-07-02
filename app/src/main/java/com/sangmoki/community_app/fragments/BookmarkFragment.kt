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
import com.sangmoki.community_app.util.FBAuth
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

        // 전체 카테고리 데이터 가져오기
        getCategoryData()
        // 북마크한 데이터 가져오기
        getBookmarkData()

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

    // 카테고리 정보 불러오기
    private fun getCategoryData() {

        // 전체 카테고리에 있는 컨텐츠 데이터 다 가져오기
        // realtime database에서 데이터 가져와 items에 담아주기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (type in dataSnapshot.children) {
                    for (data in type.children) {
                        val item = data.getValue(ContentsModel::class.java)
                        Log.d("전체 카테고리 목록 =====>", item.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBRef.categoryAll.addValueEventListener(postListener)

    }

    // 사용자가 북마크한 정보를 다 가져온다.
    private fun getBookmarkData() {

        // 북마크한 데이터 목록 가져오기
        // realtime database에서 데이터 가져와 items에 담아주기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 북마크 데이터를 가져와 bookmarkIdList에 저장
                for (data in dataSnapshot.children) {
                    Log.d("북마크한 데이터 목록 =====>", data.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        // UID에 따른 북마크 데이터
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }

    // 전체 컨텐츠 중에서 사용자가 북마크한 정보만 보여준다.
}
