package com.example.fb.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fb.MainActivity
import com.example.fb.R
import com.example.fb.adapter.ChatAdapter
import com.example.fb.model.ChatModel
import com.example.fb.vm.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class ChatFragment : Fragment(), View.OnClickListener {
    private lateinit var messageText: EditText
    private lateinit var sendButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var adapter: ChatAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var userName: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        chatViewModel = ViewModelProvider(requireActivity())[ChatViewModel::class.java]
        chatViewModel.usersMessage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val act = activity as MainActivity
        act.bottomNavigationView.visibility = View.INVISIBLE
        val view = layoutInflater.inflate(R.layout.fragment_chat, container, false)
        auth = Firebase.auth
        messageText = view.findViewById(R.id.message_text)
        sendButton = view.findViewById(R.id.send_button)
        sendButton.setOnClickListener(this)
        recyclerView = view.findViewById(R.id.rec_view_chat)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userName = auth.currentUser?.displayName.toString()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatViewModel.mesList.observe(viewLifecycleOwner) {
            adapter = ChatAdapter(it, requireContext(), this)
            recyclerView.adapter = adapter
        }
    }

    override fun onDestroy() {
        val act = activity as MainActivity
        act.bottomNavigationView.visibility = View.VISIBLE
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.send_button -> {
                chatViewModel.fireBase(messageText.text.toString(), userName)
                messageText.text = null
            }
        }
    }
}