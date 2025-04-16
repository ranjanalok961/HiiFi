package com.demo.hiifi

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object GetImage {
    fun getImage(context: Context, userId: String, callback: (String?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val bioCollection = db.collection("Bio")

        bioCollection.document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val bio = document.toObject(Bio::class.java) ?: Bio()
                    val imageUrl = bio.imageUrl ?: "default"
                    callback(imageUrl)
                } else {
                    callback("default")
                }
            }
            .addOnFailureListener { e ->
                Log.e("MyProfile", "Error fetching bio", e)
                callback(null)
            }
    }

    fun getData(context: Context, userId: String, callback: (Bio?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val bioCollection = db.collection("Bio")

        bioCollection.document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val bio = document.toObject(Bio::class.java) ?: Bio()
                    callback(bio)
                } else {
                    callback(Bio(null, null, null, null, null))
                }
            }
            .addOnFailureListener { e ->
                Log.e("MyProfile", "Error fetching bio", e)
                callback(null)
            }
    }
}
