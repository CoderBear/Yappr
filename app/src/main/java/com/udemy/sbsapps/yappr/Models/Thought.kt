package com.udemy.sbsapps.yappr.Models

import java.util.*

data class Thought constructor(val username:String, val timestamp:Date, val thoughtText: String,
                               val numLikes:Int, val numComments: Int, val documentId:String,
                               val userId: String)
