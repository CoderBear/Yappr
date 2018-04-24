package com.udemy.sbsapps.yappr

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var selectedCategory = FUNNY
    lateinit var thoughtsAdapter: ThoughtsAdapter
    val thoughts = arrayListOf<Thought>()
    val thoughtsCollectionRef = FirebaseFirestore.getInstance().collection(THOUGHTS_REF)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val addThoughtIntent = Intent(this, AddThoughtActivity::class.java)
            startActivity(addThoughtIntent)
        }

        thoughtsAdapter = ThoughtsAdapter(thoughts)
        thoughtListView.adapter = thoughtsAdapter
        val layoutManager = LinearLayoutManager(this)
        thoughtListView.layoutManager = layoutManager

        thoughtsCollectionRef.get()
                .addOnSuccessListener {snapshot ->
                    for( document in snapshot.documents) {
                        val data = document.data
                        val name = data!![USERNAME] as String
                        val timestamp =  data[TIMESTAMP] as Date
                        val thoughtTxt = data[THOUGHT_TXT] as String
                        val numLikes = data[NUM_LIKES] as Long
                        val numComments = data[NUM_COMMENTS] as Long
                        val documentId = document.id

                        val newThought = Thought(name, timestamp, thoughtTxt, numLikes.toInt(), numComments.toInt(), documentId)
                        thoughts.add(newThought)
                    }
                    thoughtsAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e("Exception", "Could not add post: $exception")
                }
    }

    fun mainFunnyClicked (view: View) {
        if(selectedCategory == FUNNY) {
            mainFunnyButton.isChecked = true
        }
        mainSeriousButton.isChecked = false
        mainCrazyButton.isChecked = false
        mainPopularButton.isChecked = false
    }
    fun mainSeriousClicked (view: View) {
        if(selectedCategory == SERIOUS) {
            mainSeriousButton.isChecked = true
        }
        mainFunnyButton.isChecked = false
        mainCrazyButton.isChecked = false
        mainPopularButton.isChecked = false
    }
    fun mainCrazyClicked (view: View) {
        if(selectedCategory == CRAZY) {
            mainCrazyButton.isChecked = true
        }
        mainFunnyButton.isChecked = false
        mainSeriousButton.isChecked = false
        mainPopularButton.isChecked = false
    }
    fun mainPopularClicked (view: View) {
        if(selectedCategory == POPULAR) {
            mainPopularButton.isChecked = true
        }
        mainFunnyButton.isChecked = false
        mainSeriousButton.isChecked = false
        mainCrazyButton.isChecked = false
    }
}
