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
import com.sangmoki.community_app.contents.ContentsActivity
import com.sangmoki.community_app.databinding.FragmentStoreBinding
import com.sangmoki.community_app.databinding.FragmentTipBinding

class TipFragment : Fragment() {

    // 바인딩 변수 선언
    private lateinit var binding: FragmentTipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // DataBinding 설정
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tip, container, false)

        // category 클릭 이벤트
        binding.categoryAll.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "all")
            startActivity(intent)
        }

        binding.categoryCook.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "cook")
            startActivity(intent)
        }

        binding.categoryEconomy.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "economy")
            startActivity(intent)
        }

        binding.categoryOneRoom.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "room")
            startActivity(intent)
        }

        binding.categoryHobby.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "hobby")
            startActivity(intent)
        }

        binding.categoryInterior.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "interior")
            startActivity(intent)
        }

        binding.categoryLife.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "life")
            startActivity(intent)
        }

        binding.categoryElse.setOnClickListener {
            val intent = Intent(context, ContentsActivity::class.java)
            intent.putExtra("category", "else")
            startActivity(intent)
        }


        // Home 버튼 클릭 이벤트
        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_homeFragment)
        }

        // Talk 버튼 클릭 이벤트
        binding.talkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_talkFragment)
        }

        // Bookmark 버튼 클릭 이벤트
        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_bookmarkFragment)
        }

        // Store 버튼 클릭 이벤트
        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_tipFragment_to_storeFragment)
        }

        return binding.root
    }
}