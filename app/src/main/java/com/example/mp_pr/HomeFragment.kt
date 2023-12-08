package com.example.mp_pr

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mp_pr.databinding.FragmentInputnumberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private  lateinit var recyclerView: RecyclerView
//    private var mList = ArrayList<CommunityDataModel>()
    private lateinit var adapter: CommunityAdapter
    private  lateinit var wirteButton : Button
    var db = Firebase.firestore
    var contentModel : ArrayList<ContentModel> = arrayListOf()
    var contentUidList : ArrayList<String> = arrayListOf()
    //val adapter = CommunityAdapter(contentModel)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = rootView.findViewById(R.id.com_recyclerView)
        wirteButton = rootView.findViewById(R.id.com_write_button)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)

        db?.collection("test")?.orderBy("timeStamp", Query.Direction.DESCENDING)?.addSnapshotListener { querySnapshot, firebaseFirestoreExection ->
            contentModel.clear()
            contentUidList.clear()
            for(snapshot in querySnapshot!!.documents) {
                val item = snapshot.toObject(ContentModel::class.java)
                contentModel.add(item!!)
                contentUidList.add(snapshot.id)
            }
            adapter.notifyDataSetChanged()
        }

        adapter = CommunityAdapter(contentModel)


        adapter.setItemClickListener(object: CommunityAdapter.ClickListener{
            override fun onClick(v: View, position: Int) {
                Toast.makeText(context, "${contentModel[position].com_content}", Toast.LENGTH_SHORT).show()

                val intent: Intent = Intent(context, CommentActivity::class.java)

                intent.putExtra("uid", contentModel[position].uid)
                intent.putExtra("comTitle", contentModel[position].com_title)
                intent.putExtra("comContent", contentModel[position].com_content)
                intent.putExtra("comTime", contentModel[position].str_timeStamp)
                intent.putExtra("contentUid", contentUidList[position])
                intent.putExtra("contentFavoritesPos", position)
                intent.putExtra("contentFavoriteCount", contentModel[position].favoriteCount)


                //Log.v("posHome", position.toString())
                startActivity(intent)

            }
        })
        wirteButton.setOnClickListener {
            val intent: Intent = Intent(context, WriteActivity::class.java)

            startActivity(intent)
        }

        recyclerView.adapter = adapter

        return rootView
    }

}
