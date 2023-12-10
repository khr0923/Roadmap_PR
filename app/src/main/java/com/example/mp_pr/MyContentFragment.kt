package com.example.mp_pr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class MyContentFragment : Fragment() {
    private  lateinit var recyclerView: RecyclerView
    var userModel : ArrayList<ContentModel> = arrayListOf()
    var contentModel : ArrayList<ContentModel> = arrayListOf()
    var contentUidList : ArrayList<String> = arrayListOf()
    private lateinit var adapter: MyContentAdapter
    var uid = FirebaseAuth.getInstance().currentUser?.uid
    var db = Firebase.firestore
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val rootView: View = inflater.inflate(R.layout.fragment_my_content, container, false)

        recyclerView = rootView.findViewById(R.id.my_com_recyclerView)

        val layoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)

        db?.collection("test")?.orderBy("timeStamp", Query.Direction.DESCENDING)?.addSnapshotListener { querySnapshot, firebaseFirestoreExection ->
            userModel.clear()
            contentModel.clear()
            contentUidList.clear()
            if (querySnapshot != null) {
                for(snapshot in querySnapshot!!.documents) {
                    val item = snapshot.toObject(ContentModel::class.java)
                    //contentModel.add(item!!)
                    if(item?.uid.equals(uid)) {
                        userModel.add(item!!)
                        contentUidList.add(snapshot.id)
                    }


                }
            }
            adapter.notifyDataSetChanged()
        }
        adapter = MyContentAdapter(userModel)

        adapter.setItemClickListener(object: MyContentAdapter.ClickListener{
            override fun onClick(v: View, position: Int) {
                Toast.makeText(context, "${userModel[position].com_content}", Toast.LENGTH_SHORT).show()

                val intent: Intent = Intent(context, CommentActivity::class.java)

                intent.putExtra("uid", userModel[position].uid)
                intent.putExtra("comTitle", userModel[position].com_title)
                intent.putExtra("comContent", userModel[position].com_content)
                intent.putExtra("comTime", userModel[position].str_timeStamp)
                intent.putExtra("contentUid", contentUidList[position])
                intent.putExtra("contentFavoritesPos", position)
                intent.putExtra("contentFavoriteCount", userModel[position].favoriteCount)




                //Log.v("posHome", position.toString())
                startActivity(intent)

            }
        })

        for(i in 0 until userModel.size) {
            Log.v("내가 쓴 글~~", userModel[i].com_content.toString())
        }

        recyclerView.adapter = adapter

        return rootView
    }




}