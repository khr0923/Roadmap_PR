package com.example.mp_pr

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.PagerSnapHelper


class RoadmapFragment : Fragment() {

    val TAG = "RoadmapFragment"
    private lateinit var dataModel: DataModel

    private  lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<DataModel>()
    private var dmList = ArrayList<DetailDataModel>()
    private lateinit var adapter: ImageAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_roadmap, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setLayoutManager(layoutManager)
        addDataToList()
        addDetailDataToList()
        adapter = ImageAdapter(mList)
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(recyclerView)
        adapter.setItemClickListener(object: ImageAdapter.ClickListener{
            override fun onClick(v: View, position: Int) {

                val intent: Intent = Intent(context, RoadmapDetailActivity::class.java)



                intent.putExtra("rdImg", dmList[position].rdDetailImg)
                intent.putExtra("rdInfo", dmList[position].rdDetailInfo)
                intent.putExtra("rdInfoCP", dmList[position].rdDetailInfo_company)
                intent.putExtra("rdInfoWork", dmList[position].rdDetailInfo_work)
                intent.putExtra("rdInfoSkill", dmList[position].rdDetailInfo_skill)

                startActivity(intent)
            }
        })
        recyclerView.adapter = adapter

        return rootView
    }



    private fun addDataToList() {
        mList.add(DataModel("프론트엔드 개발자", "프론트엔드 개발자는 백엔드 API에서 가져온 데이터의 출력, 입력을 통한 비즈니스 로직 구성과 사용자와 대화하는 사용자 인터페이스 부분을 작업하는 개발자를 말합니다.", R.drawable.front_end_cover))
        mList.add(DataModel("백엔드 개발자", "백엔드 개발은 웹 애플리케이션의 서버 측 구성 요소 생성을 의미합니다. 여기에는 데이터 저장, 처리 및 검색과 서버 측 비즈니스 논리를 처리하는 코드 작성이 포함됩니다. 데이터베이스 및 웹 서버와 함께 작업하여 프론트엔드에서 사용할 수 있는 API를 생성합니다.", R.drawable.back_end_corver))
        mList.add(DataModel("게임 개발자", "프로그래밍을 통해 맵 디자인, 캐릭터 디자인, 사운드, 각종 시스템 등을 뒤섞어, 게임이라는 하나의 결과물을 만드는 직업입니다.", R.drawable.game_corver))
        mList.add(DataModel("모바일 앱 개발자", "스마트폰이나 태블릿PC 등 모바일기기에서 사용되는 소프트웨어인 모바일 애플리케이션을 개발하고 수정하는 일을 합니다.", R.drawable.mobile_corver))
        mList.add(DataModel("데이터 분석가", "데이터 분석가는 조직이 더 나은 비지니스 의사결정을 할 수 있도록 데이터를 사용해 돕는 역할을 합니다. 수많은 데이터를 정리하고, 유용한 정보를 추출하고 데이터 분석에 기초하여 결정을 내려서 인사이트를 도출해 전달합니다.", R.drawable.data_analyst_corver))
    }



    private fun addDetailDataToList() {

        dmList.add(DetailDataModel("frontEnd",
            "프론트엔드 개발자는 백엔드 API에서 가져온 데이터의 출력, 입력을 통한 비즈니스 로직 구성과 사용자와 대화하는 사용자 인터페이스 부분을 작업하는 개발자를 말합니다.",
            "네이버, 카카오, 라인, 우아한형제들, 월급쟁이부자들 등 웹/앱을 운영 및 서비스하는 모든 기업",
            """⦁ 웹이나 어플리케이션에서 디자인을 구현 
사용자가 웹사이트나 어플리케이션에서 서비스를 이용하는 과정에서 만날 수 있는 모든 것들을 눈으로 볼 수 있게 구현하고, 또 사용자가 가장 빠르고 편리하게 서비스를 이용할 수 있는 환경을 만듬.

⦁ 사용자 편의 최적화를 위해 웹/앱의 성능을 개선
이미지 최적화, 코드 최적화, 캐싱 등의 기술을 사용하여 웹사이트나 어플리케이션의 로딩 속도를 개선

⦁ 서비스 기능을 구현
웹이나 앱의 이용자가 서비스를 이용하는 목적을 달성하기 위해 필요한 기능들을 구현

⦁ 여러 운영체제나 브라우저의 호환 가능성을 지원
시장 점유율을 높이기 위해 다양한 운영 체제, 브라우저에서 모두 작동되는 사이트를 만들거나, 각각의 운영체제, 브라우저에 최적화된 웹사이트나 앱을 만듬.

⦁ 보안에 대한 지원
보안 기능을 염두하면서 개발 업무를 진행""",
            """⦁ HTML, CSS, JavaScript 등의 언어를 사용할 수 있어야 함.
                
⦁ 이미지 최적화, 코드 최적화, 캐싱 등의 기술을 사용할 수 있어야 함.

⦁ 다양한 운영 체제, 브라우저에서 모두 작동되는 사이트를 만들거나, 각각의 운영체제, 브라우저에 최적화된 웹사이트나 앱을 만들수 있어야 함.

⦁ 웹 보안에 대한 전문 지식이 필요하며, OWASP Top 10과 같은 보안 취약점에 대한 이해가 필요"""))

        dmList.add(DetailDataModel("backEnd",
            """백엔드 개발은 웹 애플리케이션의 서버 측 구성 요소 생성을 의미합니다. 여기에는 데이터 저장, 처리 및 검색과 서버 측 비즈니스 논리를 처리하는 코드 작성이 포함됩니다. 데이터베이스 및 웹 서버와 함께 작업하여 프론트엔드에서 사용할 수 있는 API를 생성합니다.""",
            "씨드앤, 월급쟁이부자들, 트레드링스 등 웹/앱을 운영 및 서비스하는 모든 기업",
            """⦁ 서버 관리: 평소에 게임을 하거나, 수강신청, 콘서트 티켓팅 등 백엔드 개발자가 개발 및 관리하는 것이 서버
                
⦁ 데이터를 저장, 관리

⦁ 데이터 처리 시간을 줄이고, 사용자의 수를 늘리는 것 등과 같은 업무 수행

⦁ 서버 측 애플리케이션을 개발하는 업무 수행 

⦁ 백엔드 개발자의 업무는 조직마다 천차만별이지만 대개는 ❶ 과제 할당 → ❷ 과제 분석 → ❸ 개발 → ➍ 테스트(리뷰) → ➎ QA 및 버그 수정 → ➏ 배포 → ➐ 유지보수 순서로 진행""",
            """⦁ 서버에 대한 지식
                
⦁ 프로그래밍 지식

⦁ 만든 프로그램을 배포하고 안전하게 서비스할 수 있게 하는 활용 능력

⦁ 서비스 구성을 위한 전체를 보는 안목

⦁ 데이터 중심의 사고를 잘하는 능력

⦁ 동작하는 것보다 그게 동작되는 원리에 대한 관심과 에러를 해결할 수 있는 능력"""))

        dmList.add(DetailDataModel("game",
            "프로그래밍을 통해 맵 디자인, 캐릭터 디자인, 사운드, 각종 시스템 등을 뒤섞어, 게임이라는 하나의 결과물을 만드는 직업입니다.",
            """⦁ ㈜넥슨코리아, ㈜엔씨소프트, 넷마블(주)등 게임 개발을 하는 기업체
                
⦁ 응용 소프트웨어 개발과 관련된 분야
                
⦁ 교육훈련기관의 교사,강사""",
            """⦁ 계획: 어떠한 기술이 필요한지 조사하고 프로그램 구현 방법과 개발 기간 산출하고 개임 계발 계획을 수립
                
⦁ 설계: 게임 구조의 기반으로 쓰일 데이터 포맷과 파일 저장 구조와 모듈 내부 처리와 모듈의 외부 인터페이스를 설계

⦁ 구축: 기본 데이터를 생성하고 필요한 모듈을 프로그램 언어로 구현한 다음 모듈을 통합하여 게임 구축 후 디버깅

⦁ 운영 및 유지 보수: 사용자의 관점과 환경에서 다양한 방법을 사용하여 게임을 테스트하여 에러를 수정하고 버그를 찾아 재개발""",
            """⦁ 다양한 프로그래밍 언어의 습득
                
⦁ 자료구조, 알고리즘, 운영체제 등 프로그래머로서의 기본기

⦁ 프로젝트를 통합 관리할 수 있는 리더쉽

⦁ 새롭게 변화하는 환경 변화와 관련 지식에 대한 지적 욕구

⦁ 이해력, 판단력, 표현력, 심미성 등의 능력

⦁ 게임 개발에 대한 열정"""))

        dmList.add(DetailDataModel("mobile",
            "스마트폰이나 태블릿PC 등 모바일기기에서 사용되는 소프트웨어인 모바일 애플리케이션을 개발하고 수정하는 일을 합니다.",
            """⦁ 앱을 운영 및 서비스하는 모든 기업
                
⦁ 교육훈련기관의 교사,강사

⦁ 자신의 아이디어로 창업 가능""",
            """⦁ 기획: 국내외의 모바일 앱 개발 흐름 및 우리나라 사람들의 모바일기기 이용 특성에 적합한 애플리케이션 기획
                
⦁ 분석: 모바일 운영체제별로 기술 변경 사항 등에 대해 확인하고 고객의 요구 사항대로 앱을 개발하기 위한 기술과 업무 분석

⦁ 개발: 기업이나 기관이 이용하기 원하는 정보들을 체계적으로 모바일 앱을 이용하여 관리할 수 있는 앱 개발

⦁ 운영 및 유지 보수: 사용자의 관점과 환경에서 다양한 방법을 사용하여 앱을 테스트하여 에러를 수정하고 버그를 찾아 재개발""",
            """⦁ 프로젝트를 통합 관리할 수 있는 리더쉽
                
⦁ 앱 개발 플랫폼 및 도구에 대한 지식

⦁ 다양한 프로그래밍 언어의 습득

⦁ 앱 디자인 원칙 이해"""))

        dmList.add(DetailDataModel("dataAnalyst",
            """데이터 분석가는 조직이 더 나은 비지니스 의사결정을 할 수 있도록 데이터를 사용해 돕는 역할을 합니다.
수많은 데이터를 정리하고, 유용한 정보를 추출하고 데이터 분석에 기초하여 결정을 내려서 인사이트를 도출해 전달합니다.""",
            """⦁ 글로벌 금융 IT기업
                
⦁ LG, CNS, 삼성, SDS, KT, SK등 SI 기업체

⦁ SK, KT, 카카오, 네이버 등 AI 센터를 운영 및 서비스하는 기업체

⦁ 구글, 아마존, 오라클 등 클라우드 시스템 및 AI 센터를 운영 및 서비스하는 기업

⦁ 데이터 기반 솔루션을 개발하고 서비스하는 기업

⦁ 빅데이터 기반의 인공지능 솔루션 및 서비스하는 기업""",
            """⦁ 문제 정의: 분석할 데이터와 분석 목적을 결정한 후 문제를 정의하고 필요한 데이터를 수집
                
⦁ 데이터 수집: 필요한 데이터를 수집하고, 이를 정리하여 데이터베이스나 스프레드시트 등에 저장

⦁ 데이터 전처리: 데이터를 분석하기 쉽도록 전처리 작업을 수행 이 과정에서 데이터를 정제하거나 결측치를 처리하며, 데이터를 정규화하거나 스케일링하는 등의 작업을 수행

⦁ 데이터 분석: 전처리가 완료된 데이터를 분석하여, 인사이트를 도출 이를 위해 통계적 기법이나 머신러닝 알고리즘 등을 활용

⦁ 결과 보고: 분석 결과를 쉽게 이해할 수 있는 보고서나 시각화 자료를 제작하여 관련자들에게 제공

⦁ 결과 활용: 분석 결과를 기반으로 의사결정을 내리거나, 보완적인 데이터 수집이나 분석을 진행""",
            """⦁ 폭넓은 Data Science 지식 및 통계 지식
⦁ SQL, Python, R 등 프로그래밍 언어 활용 능력 

⦁ Tool 활용 능력: 추출 툴(BigQuery, MysQL, MssQL 등), 분석 툴(Google Analytics, Google Optimize 등), BI 툴 (Data Studio, Tableau, PowerBl 등)

⦁ 도메인 지식 습득 능력

⦁ Project 경험

⦁ Communication Skill"""))

    }











}