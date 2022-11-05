package com.davut.mekapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davut.mekapp.R
import com.davut.mekapp.entities.Message

class MessageAdapter(val data: ArrayList<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.message_row_left,parent,false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val messageText = holder.itemView.findViewById<TextView>(R.id.messageText)
        val senderText = holder.itemView.findViewById<TextView>(R.id.senderText)
        val dateText = holder.itemView.findViewById<TextView>(R.id.dateText)

        messageText.text = data[position].message
        senderText.text = data[position].sender
        dateText.text = data[position].time
    }

    override fun getItemCount(): Int {
        return data.size
    }

}