package com.example.mp_pr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

import kotlin.collections.ArrayList

class MyCommentFragment : Fragment() {
    private  lateinit var recyclerView: RecyclerView
    var myComment : ArrayList<ContentModel> = arrayListOf()
    var contentUidList : MutableList<String> = arrayListOf()
    var myContentUidList : ArrayList<String> = arrayListOf()
    var comments: ArrayList<ContentModel.Comment> = arrayListOf()
    var uid = FirebaseAuth.getInstance().currentUser?.uid
    var db = Firebase.firestore
    private lateinit var adapter: MyCommentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val rootView: View = inflater.inflate(R.layout.fragment_my_comment, container, false)

        recyclerView = rootView.findViewById(R.id.my_comment_recyclerView)

        val layoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)

        db?.collection("test")?.orderBy("timeStamp", Query.Direction.DESCENDING)?.addSnapshotListener { querySnapshot, firebaseFirestoreExection ->

            contentUidList.clear()
            if (querySnapshot != null) {
                for(snapshot in querySnapshot!!.documents) {
                    val item1 = snapshot.toObject(ContentModel::class.java)
                    contentUidList.add(snapshot.id)
                    Log.v("size", contentUidList.size.toString())
                }
            }
            getMyContentsOfCommentId()
            //adapter.notifyDataSetChanged()


        }

        //while(myComment)

        Handler(Looper.getMainLooper()).postDelayed({
            Log.v("mycomment", myComment.size.toString())
            adapter = MyCommentAdapter(myComment)

            adapter.setItemClickListener(object: MyCommentAdapter.ClickListener{
                override fun onClick(v: View, position: Int) {
                    Toast.makeText(context, "${myComment[position].com_content}", Toast.LENGTH_SHORT).show()

                    val intent: Intent = Intent(context, CommentActivity::class.java)

                    intent.putExtra("uid", myComment[position].uid)
                    intent.putExtra("comTitle", myComment[position].com_title)
                    intent.putExtra("comContent", myComment[position].com_content)
                    intent.putExtra("comTime", myComment[position].str_timeStamp)
                    intent.putExtra("contentUid", contentUidList[position])
                    intent.putExtra("contentFavoritesPos", position)
                    intent.putExtra("contentFavoriteCount", myComment[position].favoriteCount)




                    //Log.v("posHome", position.toString())
                    startActivity(intent)

                }
            })

            recyclerView.adapter = adapter

        }, 1000)


        return rootView
    }

    fun getMyContentsOfCommentId() {
        Log.v("size~~", contentUidList.size.toString())
        var index = 0;

        for(i in contentUidList) {
            FirebaseFirestore.getInstance()
                .collection("test")
                .document(i)
                .collection("comment")
                .orderBy("timeStamp")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    //comments.clear()
                    //myContentUidList.clear()
                    if(querySnapshot == null) return@addSnapshotListener

                    for(snapshot in querySnapshot!!.documents) {
                        val item2 = snapshot.toObject(ContentModel::class.java)
                        val item = snapshot.toObject(ContentModel.Comment::class.java)
                        if(item?.uid?.equals(uid)!! ) {
                            Log.v("댓글모음", item?.comment.toString())
                            Log.v("content모음", i)
                            myContentUidList.add(i)
                        }
                    }
                    index++;

                    Log.v("index", index.toString())
                    if(index == contentUidList.size) {
                        myContentUidList = (ArrayList(myContentUidList.distinct()))
                        getComments()
                    }

                }

        }
    }

    fun getComments() {
        Log.v("사이즈", myContentUidList.size.toString())

        for(i in myContentUidList) {
            db?.collection("test")?.orderBy("timeStamp", Query.Direction.DESCENDING)?.addSnapshotListener { querySnapshot, firebaseFirestoreExection ->
                //myComment.clear()
                if (querySnapshot != null) {
                    for(snapshot in querySnapshot!!.documents) {
                        val item = snapshot.toObject(ContentModel::class.java)
                        //Log.v("uid", snapshot.id)
                        if(snapshot.id.equals(i)) {
                            Log.v("cotentId", snapshot.id)
                            myComment.add(item!!)

                        }
                    }
                }

            }
        }



    }


}