package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handler = Handler(Handler.Callback { msg ->
            if (msg.what == 0xc) {
                val json = msg.obj.toString()
                Log.i("调试", json)
                val parseData = Gson().fromJson(json, NewsBean::class.java)
                for (news in parseData.newslist) {
                    Log.i("调试", "更新日期:\t${news.ctime}")
                    Log.i("调试", "新闻标题:\t${news.title}")
                    Log.i("调试", "新闻分类:\t${news.description}")
                    Log.i("调试", "图片网址:\t${news.picUrl}")
                    Log.i("调试", "新闻网址:\t${news.url}")
                    Log.i("调试", "\t")
                    showTxT.append("更新日期:\t${news.ctime}\n" +
                            "新闻标题:\t${news.title}\n" +
                            "新闻分类:\t${news.description}\n" +
                            "图片网址:\t${news.picUrl}\n" +
                            "新闻网址:\t${news.url}\n\n")
                }
            }
            false
        })
        jsonButton.setOnClickListener {
            Thread {
                val api = "http://api.tianapi.com/wxnew/?key=d4f074e5a8866ff604bd1ee6b981eb96&num=20"
                val message = Message()
                message.obj = GsonBuilder().setPrettyPrinting().create().toJson(JsonParser()
                        .parse(OkHttpClient().newCall(Request.Builder().url(api).build())
                                .execute().body()!!.string()))
                message.what = 0xc
                handler.sendMessage(message)
            }.start()
        }
    }
}
