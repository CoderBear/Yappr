package com.udemy.sbsapps.yappr.Activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.firebase.firestore.FirebaseFirestore
import com.udemy.sbsapps.yappr.R
import com.udemy.sbsapps.yappr.Utilities.THOUGHTS_REF
import com.udemy.sbsapps.yappr.Utilities.THOUGHT_DOC_ID_EXTRA
import com.udemy.sbsapps.yappr.Utilities.THOUGHT_TXT
import com.udemy.sbsapps.yappr.Utilities.THOUGHT_TXT_EXTRA
import kotlinx.android.synthetic.main.activity_update_thought.*

class UpdateThoughtActivity : AppCompatActivity() {

    lateinit var thoughtDocID : String
    lateinit var thoughtTxt : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_thought)

        thoughtDocID = intent.getStringExtra(THOUGHT_DOC_ID_EXTRA)
        thoughtTxt = intent.getStringExtra(THOUGHT_TXT_EXTRA)

        updatThoughtTxt.setText(thoughtTxt)
    }

    fun updateThoughtClicked(view: View) {
        FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thoughtDocID)
                .update(THOUGHT_TXT, updatThoughtTxt.text.toString())
                .addOnSuccessListener {
                    hideKeyboard()
                    finish()
                }
                .addOnFailureListener { exception ->
                    Log.e("Exception:", "Could not update thought: ${exception.localizedMessage}")
                }
    }

    private fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}
