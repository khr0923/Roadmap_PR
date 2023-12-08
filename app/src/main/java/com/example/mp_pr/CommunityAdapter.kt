package com.example.mp_pr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

class CommunityAdapter(val contentModel: ArrayList<ContentModel>) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contentModel.size
    }

    interface  ClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(itemclickListener: ClickListener) {
        this.ItemClickListener = itemclickListener
    }

    private lateinit var ItemClickListener : ClickListener

    override fun onBindViewHolder(holder: CommunityAdapter.ViewHolder, position: Int) {
        holder.title.text = contentModel[position].com_title
        holder.content.text = contentModel[position].com_content
        holder.time_stamp.text = contentModel[position].str_timeStamp
        holder.favorite_count.text = contentModel[position].favoriteCount.toString()
        holder.comment_count.text = contentModel[position].commentCount.toString()

        holder.itemView.setOnClickListener {
            ItemClickListener.onClick(it, position)
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById((R.id.com_title))
        val content : TextView = itemView.findViewById(R.id.com_content)
        val time_stamp : TextView = itemView.findViewById(R.id.com_time)
        val favorite_count : TextView = itemView.findViewById(R.id.com_favorite_count)
        val comment_count : TextView = itemView.findViewById(R.id.com_comment_count)
    }

}