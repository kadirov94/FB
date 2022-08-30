package com.example.fb.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fb.model.ChatModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatViewModel : ViewModel() {

    private var fireBase = Firebase
        .database("https://fir-test-a70fa-default-rtdb.asia-southeast1.firebasedatabase.app")
        .getReference("messages")

    val mesList: MutableLiveData<MutableList<ChatModel>> = MutableLiveData()

    fun fireBase(message: String, userName: String) {
        fireBase.child(fireBase.push().key ?: "newPath")
            .setValue(ChatModel(message, userName))
        usersMessage()
    }

    fun usersMessage() {
        val allChat: MutableList<ChatModel> = mutableListOf()
        fireBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (s in snapshot.children) {
                    val user = s.getValue(ChatModel::class.java)
                    if (user != null) allChat.add(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }).apply {
            mesList.value = allChat
        }
    }
}