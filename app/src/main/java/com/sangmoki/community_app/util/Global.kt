package com.sangmoki.community_app.util

import java.text.SimpleDateFormat
import java.util.Locale

// 글로벌하게 사용할 함수 담아준 클래스
class Global {

    companion object {

        // 현재 시간 가져오는 함수 - 한국 기준 String 값 리턴 - Model 사용 시 String으로 주어야 함
        fun getTime(): String {
            val currentDateTime = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

            return dateFormat
        }
    }
}