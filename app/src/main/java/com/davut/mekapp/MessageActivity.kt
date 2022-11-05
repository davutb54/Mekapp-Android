package com.davut.mekapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davut.mekapp.adapters.MessageAdapter
import com.davut.mekapp.entities.Message
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessageActivity : AppCompatActivity() {

    val db = Firebase.firestore

    var messages = ArrayList<Message>()
    private lateinit var recyclerViewAdapter : MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        getData()
        //getMessages()

        val recyclerView = findViewById<RecyclerView>(R.id.conversationRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = MessageAdapter(messages)
        recyclerView.adapter = recyclerViewAdapter
    }

    fun getData(){
        db.collection("messages2").addSnapshotListener { snapshot, error ->
            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {
                        val documents = snapshot.documents
                        messages.clear()

                        for (document in documents) {
                            val message = document.get("message") as String
                            val sender = document.get("sender") as String
                            val date = document.get("time") as String

                            val item = Message(sender,message,date)
                            messages.add(item)
                        }

                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }


    fun getMessages() {
        db.collection("messages").get().addOnSuccessListener { documents ->
            for (document in documents) {
                messages.add(
                    Message(
                        document.data.get("sender") as String,
                        document.data.get("message") as String,
                        document.data.get("time") as String
                    )
                )
            }
        }
    }

    fun send(view: View) {
        val messageText = findViewById<EditText>(R.id.messageEditText)

        val message = messageText.text.toString()
        val sender = "davutb54"
        val time = "14:10"
        val date = Timestamp.now().toString()

        val data = hashMapOf<String,Any>(
            "message" to message,
            "sender" to sender,
            "time" to time,
            "date" to date,
        )

        db.collection("messages2").add(data).addOnSuccessListener { document ->
            Log.i("doc",document.toString())
            messageText.text.clear()
        }
    }
}