package com.udemy.sbsapps.yappr.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.udemy.sbsapps.yappr.*
import com.udemy.sbsapps.yappr.Adapaters.ThoughtsAdapter
import com.udemy.sbsapps.yappr.Interfaces.ThoughtOptionsClickListener
import com.udemy.sbsapps.yappr.Models.Thought
import com.udemy.sbsapps.yappr.R
import com.udemy.sbsapps.yappr.Utilities.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity(), ThoughtOptionsClickListener {

    override fun thoughtOptionsMenuClicked(thought: Thought) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var selectedCategory = FUNNY
    lateinit var thoughtsAdapter: ThoughtsAdapter
    val thoughts = arrayListOf<Thought>()
    val thoughtsCollectionRef = FirebaseFirestore.getInstance().collection(THOUGHTS_REF)
    lateinit var thoughtsListener : ListenerRegistration
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val addThoughtIntent = Intent(this, AddThoughtActivity::class.java)
            startActivity(addThoughtIntent)
        }

        thoughtsAdapter = ThoughtsAdapter(thoughts, this) { thought ->
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra(DOCUMENT_KEY, thought.documentId)
            startActivity(intent)
        }
        thoughtListView.adapter = thoughtsAdapter
        val layoutManager = LinearLayoutManager(this)
        thoughtListView.layoutManager = layoutManager
        auth = FirebaseAuth.getInstance()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val menuItem = menu.getItem(0)
        if(auth.currentUser == null) {
            //logged out state
            menuItem.title = "Login"
        } else {
            // logged in
            menuItem.title = "Logout"
        }
        return super.onPrepareOptionsMenu(menu)
    }

    fun updateUI() {
        if(auth.currentUser == null) {
            mainCrazyButton.isEnabled = false
            mainPopularButton.isEnabled = false
            mainSeriousButton.isEnabled = false
            mainFunnyButton.isEnabled = false
            fab.isEnabled = false
            thoughts.clear()
            thoughtsAdapter.notifyDataSetChanged()
        } else {
            mainCrazyButton.isEnabled = true
            mainPopularButton.isEnabled = true
            mainSeriousButton.isEnabled = true
            mainFunnyButton.isEnabled = true
            fab.isEnabled = true
            setListener()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.action_login)
        {
            if(auth.currentUser == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                auth.signOut()
                updateUI()
            }
            return true
        }
        return false
    }

    fun setListener() {

        if(selectedCategory == POPULAR){
            // shopwshow Popular documents
            thoughtsListener = thoughtsCollectionRef
                    .orderBy(NUM_LIKES, Query.Direction.DESCENDING)
                    .addSnapshotListener(this) { snapshot, exception ->
                        if (exception != null) {
                            Log.e("Exception", "Could not retrieve documents: $exception")
                        }
                        if (snapshot != null) {
                            parseData(snapshot)
                        }
                    }
        } else {
            thoughtsListener = thoughtsCollectionRef
                    .orderBy(TIMESTAMP, Query.Direction.DESCENDING)
                    .whereEqualTo(CATEGORY, selectedCategory)
                    .addSnapshotListener(this) { snapshot, exception ->
                        if (exception != null) {
                            Log.e("Exception", "Could not retrieve documents: $exception")
                        }
                        if (snapshot != null) {
                            parseData(snapshot)
                        }
                    }
        }
    }

    fun parseData(snapshot: QuerySnapshot) {
        thoughts.clear()
        for (document in snapshot.documents) {
            val data = document.data
            Log.i("data", data.toString())
            val name = data[USERNAME] as String
            val timestamp = data[TIMESTAMP] as Date
            val thoughtTxt = data[THOUGHT_TXT] as String
            val numLikes = data[NUM_LIKES] as Long
            val numComments = data[NUM_COMMENTS] as Long
            val documentId = document.id
            val userId = data[USER_ID] as String

            val newThought = Thought(name, timestamp, thoughtTxt, numLikes.toInt(), numComments.toInt(), documentId, userId)
            thoughts.add(newThought)
        }
        thoughtsAdapter.notifyDataSetChanged()
    }

    fun mainFunnyClicked (view: View) {
        if(selectedCategory == FUNNY) {
            mainFunnyButton.isChecked = true
            return
        }
        mainSeriousButton.isChecked = false
        mainCrazyButton.isChecked = false
        mainPopularButton.isChecked = false
        selectedCategory = FUNNY
        thoughtsListener.remove()
        setListener()
    }
    fun mainSeriousClicked (view: View) {
        if(selectedCategory == SERIOUS) {
            mainSeriousButton.isChecked = true
            return
        }
        mainFunnyButton.isChecked = false
        mainCrazyButton.isChecked = false
        mainPopularButton.isChecked = false
        selectedCategory = SERIOUS

        thoughtsListener.remove()
        setListener()
    }
    fun mainCrazyClicked (view: View) {
        if(selectedCategory == CRAZY) {
            mainCrazyButton.isChecked = true
            return
        }
        mainFunnyButton.isChecked = false
        mainSeriousButton.isChecked = false
        mainPopularButton.isChecked = false
        selectedCategory = CRAZY

        thoughtsListener.remove()
        setListener()
    }
    fun mainPopularClicked (view: View) {
        if(selectedCategory == POPULAR) {
            mainPopularButton.isChecked = true
            return
        }
        mainFunnyButton.isChecked = false
        mainSeriousButton.isChecked = false
        mainCrazyButton.isChecked = false
        selectedCategory = POPULAR

        thoughtsListener.remove()
        setListener()
    }
}
