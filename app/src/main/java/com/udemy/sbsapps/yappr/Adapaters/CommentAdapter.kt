package com.udemy.sbsapps.yappr.Adapaters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.udemy.sbsapps.yappr.Models.Thought
import com.udemy.sbsapps.yappr.R
import com.udemy.sbsapps.yappr.Utilities.NUM_LIKES
import com.udemy.sbsapps.yappr.Utilities.THOUGHTS_REF
import java.text.SimpleDateFormat
import java.util.*

class CommentAdapter(val comments: ArrayList<Comment>, val itemClick: (Comment) -> Unit) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.count()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindComment(comments[position])
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val username =  itemView?.findViewById<TextView>(R.id.commentListUsername)
        val timestamp =  itemView?.findViewById<TextView>(R.id.commentListTimestamp)
        val commentTxt =  itemView?.findViewById<TextView>(R.id.commentListCommentTxt)


        fun bindComment(comment: Comment) {
            username?.text = comment.username
            commentTxt?.text = comment.commentTxt

            val dateFormatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
            val dateString = dateFormatter.format(comment.timestamp)
            timestamp?.text = dateString
            itemView.setOnClickListener { itemClick(comment) }
        }
    }
}