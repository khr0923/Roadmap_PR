package com.example.mp_pr

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mp_pr.databinding.CommentItemViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: CommentItemViewBinding

    lateinit var comDetailTitle: TextView
    lateinit var comDetailContent: TextView
    lateinit var comDetailTime: TextView
    lateinit var commentCount: TextView
    lateinit var comment_recyclerView: RecyclerView
    lateinit var content_delete_btn: Button
    lateinit var content_favorite_btn: Button
    lateinit var com_favorite_count: TextView

    lateinit var comment_btn: Button
    lateinit var write_comment: EditText

    lateinit var str_title: String
    lateinit var str_contnet: String
    lateinit var str_time: String
    var str_comment_count: String = "0"

    var contentUid : String? = null
    var currentUid : String? = null
    var contentPosition : Int? = null
    var comments: ArrayList<ContentModel.Comment> = arrayListOf()
    var commentIdList : ArrayList<String> = arrayListOf()
    var comment = ContentModel.Comment()
    val db = Firebase.firestore
    var uid = FirebaseAuth.getInstance().currentUser?.uid
    var contentModel = ContentModel()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        contentUid = intent.getStringExtra("contentUid")
        currentUid = intent.getStringExtra("uid")
        contentPosition = intent.getIntExtra("contentFavoritesPos", 0)
        Log.v("posComAc", contentPosition.toString())


        content_delete_btn = findViewById<Button>(R.id.content_delete_btn)
        content_favorite_btn = findViewById<Button>(R.id.content_favorite_btn)

        comment_recyclerView = findViewById<RecyclerView>(R.id.comment_recyclerView)
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        comment_recyclerView.adapter = CommentItemViewRecyclerviewAdapter()
        comment_recyclerView.setLayoutManager(layoutManager)


        comDetailTitle = findViewById<TextView>(R.id.com_detail_title)
        str_title = intent.getStringExtra("comTitle").toString()
        comDetailTitle.setText(str_title)

        comDetailContent = findViewById<TextView>(R.id.com_detail_content)
        str_contnet = intent.getStringExtra("comContent").toString()
        comDetailContent.setText(str_contnet)

        comDetailTime = findViewById<TextView>(R.id.com_detail_time)
        str_time = intent.getStringExtra("comTime").toString()
        comDetailTime.setText(str_time)

        com_favorite_count = findViewById<TextView>(R.id.com_favorite_count)

//        commentCount = findViewById(R.id.comment_count)
//        commentCount.setText(comments.size)


        comment_btn = findViewById<Button>(R.id.comment_btn)
        write_comment = findViewById<EditText>(R.id.write_comment)
        comment_btn.setOnClickListener {

            comment.uid = FirebaseAuth.getInstance().currentUser?.uid
            comment.comment = write_comment.text.toString()
            comment.timeStamp = System.currentTimeMillis()

            FirebaseFirestore.getInstance().collection("test").document(contentUid!!).collection("comment").document().set(comment)
            write_comment.setText("")

        }

        commentCount = findViewById<TextView>(R.id.comment_count)



        db?.collection("test")?.document(contentUid!!)
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(querySnapshot == null) return@addSnapshotListener
                var contentDTO = querySnapshot.toObject(ContentModel::class.java)
                if(contentDTO != null){
                    com_favorite_count.setText(contentDTO.favoriteCount.toString())
                    //commentCount.setText(contentDTO.)
                }

            }



        if(currentUid.equals(FirebaseAuth.getInstance().currentUser?.uid.toString())) {
            content_delete_btn.visibility = View.VISIBLE
//                holder.binding.comment.setTextColor(Color.RED)
//                holder.binding.commentDeleteBtn.visibility = View.VISIBLE

        }

        content_delete_btn.setOnClickListener {
            db.collection("test").document(contentUid.toString())
                .delete()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        finish()

                    }
                }
        }

        content_favorite_btn.setOnClickListener {
            favoriteEvent()
            Log.v("id", uid.toString())
            Log.v("좋아요 누른 사람", contentModel.favorites.containsKey(uid).toString())
        }


//        if(contentModels.favorites.containsKey(uid)) {
//            content_favorite_btn.visibility = View.INVISIBLE
//        }
//        else{
//            content_favorite_btn.visibility = View.VISIBLE
//        }

        db?.collection("test")?.document(contentUid!!)
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if(querySnapshot == null) return@addSnapshotListener
                var contentDTO = querySnapshot.toObject(ContentModel::class.java)
                if(contentDTO != null){
                    if(contentDTO?.favorites?.containsKey(uid)!!) {
                        content_favorite_btn.visibility = View.INVISIBLE
                    }
                    else{
                        content_favorite_btn.visibility = View.VISIBLE
                    }
                }

            }


    }
    fun favoriteEvent() {
        var tsDoc = db?.collection("test")?.document(contentUid.toString())
        db?.runTransaction {transaction ->
            contentModel = transaction.get(tsDoc!!).toObject(ContentModel::class.java)!!

            if(contentModel!!.favorites.containsKey(uid)) {
                contentModel?.favoriteCount = contentModel.favoriteCount - 1
                contentModel?.favorites?.remove(uid)
            } else {
                contentModel?.favoriteCount = contentModel.favoriteCount + 1
                contentModel?.favorites!![uid!!] = true
            }
            transaction.set(tsDoc, contentModel)
        }



    }

    inner class CommentItemViewHolder(var binding: CommentItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    inner class CommentItemViewRecyclerviewAdapter() : RecyclerView.Adapter<CommentItemViewHolder>() {
        //lateinit var commentUID : String
        init{
            FirebaseFirestore.getInstance()
                .collection("test")
                .document(contentUid!!)
                .collection("comment")
                .orderBy("timeStamp")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    comments.clear()
                    commentIdList.clear()
                    if(querySnapshot == null) return@addSnapshotListener

                    for(snapshot in querySnapshot.documents!!) {
                        comments.add(snapshot.toObject(ContentModel.Comment::class.java)!!)

                        commentIdList.add(snapshot.id)
                    }
                    notifyDataSetChanged()




                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
            var view = CommentItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CommentItemViewHolder(view)
        }

        override fun getItemCount(): Int {
            return comments.size
        }

        override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
            lateinit var commentUID : String
            var comment = comments[position]
            var commentId = commentIdList[position]
            var deleteCount = 0;

            holder.binding.comment.text = comment.comment
            var now : Long? = comment.timeStamp
            var sdf = SimpleDateFormat("MM-dd HH:mm")
            var time_result = sdf.format(now)
            holder.binding.commentTime.text = time_result.toString()

            commentUID = comment.uid.toString()



            if(commentUID.equals(FirebaseAuth.getInstance().currentUser?.uid.toString())) {
                holder.binding.comment.setTextColor(Color.RED)
                holder.binding.commentDeleteBtn.visibility = View.VISIBLE
//                Log.v("태그", commentUID)
//                Log.v("태그", FirebaseAuth.getInstance().currentUser?.uid.toString())
            }

            holder.binding.commentDeleteBtn.setOnClickListener {
                Log.v("태그", "삭제버튼 클릭")
                Log.v("태그", commentId)
                val db = Firebase.firestore
                if(comments.size == 1) {
                    commentCount.setText("0")
                    var tsDoc1 = db?.collection("test")?.document(contentUid.toString())
                    db?.runTransaction {transaction ->
                        contentModel = transaction.get(tsDoc1!!).toObject(ContentModel::class.java)!!

                        Log.v("model2", contentModel.commentCount.toString())
                        contentModel?.commentCount = 0
                        transaction.set(tsDoc1, contentModel)
                    }
                    Log.v("model", contentModel.commentCount.toString())
                    deleteCount++;
                }
                //else deleteCount = 0;
                //Log.v("count", deleteCount.toString())
                db.collection("test").document(contentUid.toString()).collection("comment").document(commentId)
                    .delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                        }
                    }
            }

            if(contentModel.commentCount == 1 && deleteCount == 1) {
                Log.v("aaa", "댓글 한개")
            }

            str_comment_count = comments.size.toString()

            commentCount.setText(str_comment_count)

            var tsDoc1 = db?.collection("test")?.document(contentUid.toString())
            db?.runTransaction {transaction ->
                contentModel = transaction.get(tsDoc1!!).toObject(ContentModel::class.java)!!

                Log.v("model2", contentModel.commentCount.toString())
                contentModel?.commentCount = comments.size
                transaction.set(tsDoc1, contentModel)
            }


        }

    }




}
