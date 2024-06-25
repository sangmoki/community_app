package com.sangmoki.community_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sangmoki.community_app.R
import com.sangmoki.community_app.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    // 바인딩 변수 선언
    private lateinit var binding: FragmentHomeBinding

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
        

        return binding.root
    }

}