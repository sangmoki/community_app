package com.sangmoki.community_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sangmoki.community_app.R
import com.sangmoki.community_app.adapter.BookmarkRvAdapter
import com.sangmoki.community_app.databinding.FragmentHomeBinding
import com.sangmoki.community_app.model.BookmarkModel
import com.sangmoki.community_app.model.ContentsModel
import com.sangmoki.community_app.util.FBRef


class HomeFragment : Fragment() {

    // 바인딩 변수 선언
    private lateinit var binding: FragmentHomeBinding

    // 북마크 ID를 담기 위한 객체 생성
    private lateinit var bookmarkRvAdapter : BookmarkRvAdapter

    // 전체 카테고리 데이터 담기 위한 객체 생성
    val items = ArrayList<ContentsModel>()
    // 북마크 ID를 담기 위한 객체 생성
    val bookmarkIdList = mutableListOf<String>()
    // 전체 카테고리 데이터의 keyId를 찾기 위한 객체 생성
    val itemKeyList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // DataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // Tip 버튼 클릭 이벤트
        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_tipFragment)
        }

        // Talk 버튼 클릭 이벤트
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_talkFragment)
        }

        // Bookmark 버튼 클릭 이벤트
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_bookmarkFragment)
        }

        // Stroe 버튼 클릭 이벤트
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_storeFragment)
        }

        // 어댑터 설정 - fragment에서 context 넣을 때는 requireContext() 사용
        bookmarkRvAdapter = BookmarkRvAdapter(requireContext(), items, bookmarkIdList, itemKeyList)
        val rv : RecyclerView = binding.mainRv
        rv.adapter = bookmarkRvAdapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

        // 전체 카테고리 데이터 가져오기
        getCategoryData()

        return binding.root
    }

    private fun getCategoryData() {

        // 전체 카테고리에 있는 컨텐츠 데이터 다 가져오기
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (type in dataSnapshot.children) {
                    for (data in type.children) {
                        val item = data.getValue(ContentsModel::class.java)
                        items.add(item!!)
                        itemKeyList.add(data.key.toString())
                    }
                }
                // 비동기 처리이기 때문에 데이터를 가져온 후 어댑터에 업데이트 한다.
                bookmarkRvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FBRef.categoryAll.addValueEventListener(postListener)

    }

}