package com.example.mp_pr

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class RoadmapDetailActivity : AppCompatActivity() {

    lateinit var roadmap_back: ImageButton
    lateinit var roadmapImage: ImageView
    lateinit var roadmapName: TextView
    lateinit var roadmapNameInfo: TextView
    lateinit var roadmapInfo: TextView
    lateinit var roadmapInfo_company: TextView
    lateinit var roadmapInfo_work: TextView
    lateinit var roadmapInfo_skill: TextView

    lateinit var str_img: String
    lateinit var str_info: String
    lateinit var str_info_company: String
    lateinit var str_info_work: String
    lateinit var str_info_skill: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roadmap_detail)

        roadmap_back = findViewById<ImageButton>(R.id.roadmap_back)

        roadmap_back.setOnClickListener {
            finish()
        }

        roadmapImage = findViewById<ImageView>(R.id.roadmapImage)
        roadmapName = findViewById<TextView>(R.id.roadmapName)
        roadmapNameInfo = findViewById<TextView>(R.id.roadmapNameInfo)
        str_img = intent.getStringExtra("rdImg").toString()

        when (str_img) {
            "frontEnd" -> {
                roadmapImage.setImageResource(R.drawable.rd_front_end)
                roadmapName.setText("프론트엔드 개발자")
                roadmapNameInfo.setText("1️⃣ 프론트엔드 개발자란?")

            }
            "backEnd" -> {
                roadmapImage.setImageResource(R.drawable.rd_back_end)
                roadmapName.setText("백엔드 개발자")
                roadmapNameInfo.setText("1️⃣ 백엔드 개발자란?")
            }
            "game" -> {
                roadmapImage.setImageResource(R.drawable.rd_game)
                roadmapName.setText("게임 개발자")
                roadmapNameInfo.setText("1️⃣ 게임 개발자란?")
            }
            "mobile" -> {
                roadmapImage.setImageResource(R.drawable.rd_mobile)
                roadmapName.setText("모바일 앱 개발자")
                roadmapNameInfo.setText("1️⃣ 모바일 앱 개발자란?")
            }
            "dataAnalyst" -> {
                roadmapImage.setImageResource(R.drawable.rd_data_analyst)
                roadmapName.setText("데이터 분석가")
                roadmapNameInfo.setText("1️⃣ 데이터 분석가란?")
            }
        }


        roadmapInfo = findViewById<TextView>(R.id.roadmapInfo)
        str_info = intent.getStringExtra("rdInfo").toString()
        roadmapInfo.setText(str_info)

        roadmapInfo_company = findViewById<TextView>(R.id.roadmapInfo_company)
        str_info_company = intent.getStringExtra("rdInfoCP").toString()
        str_info_company.replace(" ", "\u00A0");
        roadmapInfo_company.setText(str_info_company)
        roadmapInfo_company.text = SpannableStringBuilder(str_info_company).apply {
            setSpan(IndentLeadingMarginSpan(), 0, length, 0)
        }

        roadmapInfo_work = findViewById<TextView>(R.id.roadmapInfo_work)
        str_info_work = intent.getStringExtra("rdInfoWork").toString()
        str_info_work.replace(" ", "\u00A0");
        roadmapInfo_work.setText(str_info_work)
        roadmapInfo_work.text = SpannableStringBuilder(str_info_work).apply {
            setSpan(IndentLeadingMarginSpan(), 0, length, 0)
        }

        roadmapInfo_skill = findViewById<TextView>(R.id.roadmapInfo_skill)
        str_info_skill = intent.getStringExtra("rdInfoSkill").toString()
        str_info_skill.replace(" ", "\u00A0");
        roadmapInfo_skill.setText(str_info_skill)
        roadmapInfo_skill.text = SpannableStringBuilder(str_info_skill).apply {
            setSpan(IndentLeadingMarginSpan(), 0, length, 0)
        }

    }
}