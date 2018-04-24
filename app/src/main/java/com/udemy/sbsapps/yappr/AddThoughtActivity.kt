package com.udemy.sbsapps.yappr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_thought.*

class AddThoughtActivity : AppCompatActivity() {

    var selectedCategory = FUNNY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_thought)
    }

    fun addPostClicked (view: View) {
        // add post to Firestore
        val data = HashMap<String, Any>()
        data.put("category", selectedCategory)
        data.put("numComments",0)
        data.put("numLikes",0)
        data.put("thoughtText",addThoughtText.text.toString())
        data.put("timestamp", FieldValue.serverTimestamp())
        data.put("username", addUsernameText.text.toString())

        FirebaseFirestore.getInstance().collection(THOUGHTS_REF)
                .add(data)
                .addOnSuccessListener {
                    finish()
                }
                .addOnFailureListener { exception ->
                    Log.e("Exception", "Could not add post: $exception")
                }
    }
    fun addFunnyClicked (view: View) {
        if(selectedCategory == FUNNY) {
            addFunnyButton.isChecked = true
        }
        addSeriousButton.isChecked = false
        addCrazyButton.isChecked = false
    }
    fun addSeriousClicked (view: View) {
        if(selectedCategory == SERIOUS) {
            addSeriousButton.isChecked = true
        }
        addFunnyButton.isChecked = false
        addCrazyButton.isChecked = false
    }
    fun addCrazyClicked (view: View) {
        if(selectedCategory == CRAZY) {
            addCrazyButton.isChecked = true
        }
        addFunnyButton.isChecked = false
        addSeriousButton.isChecked = false
    }
}
