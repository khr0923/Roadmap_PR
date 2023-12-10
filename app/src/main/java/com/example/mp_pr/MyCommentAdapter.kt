package com.example.mp_pr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyCommentAdapter(val contentModel: ArrayList<ContentModel>) : RecyclerView.Adapter<MyCommentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_page_comment_item_view, parent, false)
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

    override fun onBindViewHolder(holder: MyCommentAdapter.ViewHolder, position: Int) {
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
        val title : TextView = itemView.findViewById((R.id.my_com_title))
        val content : TextView = itemView.findViewById(R.id.my_com_content)
        val time_stamp : TextView = itemView.findViewById(R.id.my_com_time)
        val favorite_count : TextView = itemView.findViewById(R.id.my_com_favorite_count)
        val comment_count : TextView = itemView.findViewById(R.id.my_com_comment_count)
    }
}