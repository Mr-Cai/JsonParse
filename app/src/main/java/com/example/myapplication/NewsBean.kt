package com.example.myapplication

import com.google.gson.annotations.SerializedName

class NewsBean {
    @SerializedName("newslist")
    lateinit var newslist: List<News>

    data class News(
            var ctime: String,
            var title: String,
            var description: String,
            var picUrl: String,
            var url: String
    )
}