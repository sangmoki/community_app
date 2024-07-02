package com.sangmoki.community_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sangmoki.community_app.R
import com.sangmoki.community_app.adapter.BookmarkRvAdapter
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
    // 북마크 어댑터 정의
    private lateinit var bookmarkRvAdapter: BookmarkRvAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmark, container, false)


        // 북마크한 데이터 가져오기
        getBookmarkData()
        // 전체 컨텐츠 중 사용자가 북마크한 정보만 보여준다. (필터링)

        // 어댑터 설정 - fragment에서 context 넣을 때는 requireContext() 사용
        bookmarkRvAdapter = BookmarkRvAdapter(requireContext(), items, bookmarkIdList, itemKeyList)
        val rv : RecyclerView = binding.bookmarkRv
        rv.adapter = bookmarkRvAdapter
        rv.layoutManager = GridLayoutManager(requireContext(), 2)

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
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (type in dataSnapshot.children) {
                    for (data in type.children) {
                        // realtime database에서 데이터 가져와 items에 담아주기
                        val item = data.getValue(ContentsModel::class.java)

                        if (bookmarkIdList.contains(data.key.toString())) {
                            items.add(item!!)
                            itemKeyList.add(data.key.toString())
                        }
                    }
                }
                // 비동기 처리이기 때문에 데이터를 가져온 후 어댑터에 업데이트 한다.
                bookmarkRvAdapter.notifyDataSetChanged()
                Log.d("전체 카테고리 목록 ====>>", items.toString())
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
                    bookmarkIdList.add(data.key.toString())
                }
                Log.d("북마크한 데이터 목록 ====>>", bookmarkIdList.toString())

                // 전체 카테고리 데이터 가져오기
                getCategoryData()

                // 비동기 처리이기 때문에 어댑터 업데이트 해주어야 한다.
                bookmarkRvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        // UID에 따른 북마크 데이터
        FBRef.bookmarkRef.child(FBAuth.getUid()).addValueEventListener(postListener)

    }

    // 전체 컨텐츠 중에서 사용자가 북마크한 정보만 보여준다.
}
