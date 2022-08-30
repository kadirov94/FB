package com.example.fb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fb.MainActivity
import com.example.fb.R
import com.example.fb.model.ChatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class ChatAdapter(
    private val list: MutableList<ChatModel>,
    val context: Context,
    val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {
    private lateinit var auth: FirebaseAuth

    class ChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        var messageUser1: TextView = view.findViewById(R.id.user1_text)
        var messageUser2: TextView = view.findViewById(R.id.user2_text)
        var cardUser1: CardView = view.findViewById(R.id.user1_card)
        var cardUser2: CardView = view.findViewById(R.id.user2_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        auth = Firebase.auth
        val itemData = list[position]
        if (itemData.userName == auth.currentUser?.displayName){
            holder.messageUser1.text = itemData.messageUser
            holder.cardUser2.visibility = View.INVISIBLE
        } else {
            holder.messageUser2.text = itemData.messageUser
            holder.cardUser1.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}