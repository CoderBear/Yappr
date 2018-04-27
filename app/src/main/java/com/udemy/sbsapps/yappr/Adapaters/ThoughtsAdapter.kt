package com.udemy.sbsapps.yappr.Adapaters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.udemy.sbsapps.yappr.Interfaces.ThoughtOptionsClickListener
import com.udemy.sbsapps.yappr.Utilities.NUM_LIKES
import com.udemy.sbsapps.yappr.R
import com.udemy.sbsapps.yappr.Utilities.THOUGHTS_REF
import com.udemy.sbsapps.yappr.Models.Thought
import java.text.SimpleDateFormat
import java.util.*

class ThoughtsAdapter(val thoughts: ArrayList<Thought>, val thoughtOptionsClickListener: ThoughtOptionsClickListener, val itemClick: (Thought) -> Unit) : RecyclerView.Adapter<ThoughtsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.thought_list_view, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return thoughts.count()
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindThought(thoughts[position])
    }

    inner class ViewHolder(itemView: View?, val itemClick: (Thought) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val username =  itemView?.findViewById<TextView>(R.id.listViewUsername)
        val timestamp =  itemView?.findViewById<TextView>(R.id.listViewTimestamp)
        val thoughtTxt =  itemView?.findViewById<TextView>(R.id.listViewThoughtText)
        val numLikes =  itemView?.findViewById<TextView>(R.id.listViewNumLikesLabel)
        val likesImage =  itemView?.findViewById<ImageView>(R.id.listViewLikesImage)
        val numCommets = itemView?.findViewById<TextView>(R.id.listViewNumCommetsLabel)
        val optionsImage = itemView?.findViewById<ImageView>(R.id.thoughOptionsImage)

        fun bindThought(thought: Thought) {
            optionsImage?.visibility = View.INVISIBLE
            username?.text = thought.username
            thoughtTxt?.text = thought.thoughtText
            numLikes?.text = thought.numLikes.toString()
            numCommets?.text = thought.numComments.toString()

            val dateFormatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
            val dateString = dateFormatter.format(thought.timestamp)
            timestamp?.text = dateString
            itemView.setOnClickListener { itemClick(thought) }

            likesImage?.setOnClickListener {
                FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thought.documentId)
                        .update(NUM_LIKES, thought.numLikes + 1)
            }

            if(FirebaseAuth.getInstance().currentUser?.uid == thought.userId) {
                optionsImage?.visibility = View.VISIBLE
                optionsImage?.setOnClickListener {
                    thoughtOptionsClickListener.thoughtOptionsMenuClicked(thought)
                }
            }

        }
    }
}