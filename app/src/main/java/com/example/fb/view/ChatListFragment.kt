package com.example.fb.view

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.fb.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatListFragment : Fragment(), View.OnClickListener {
    private lateinit var cardView: CardView
    private lateinit var userImage: ImageView
    private lateinit var userName: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = layoutInflater.inflate(R.layout.fragment_chat_list, container, false)
        auth = Firebase.auth
        getUserData()
        cardView = view.findViewById(R.id.card_view)
        cardView.setOnClickListener(this)
        userImage = view.findViewById(R.id.chat_user_image)
        userName = view.findViewById(R.id.chat_user_name)
        return view
    }

    private fun getUserData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val userIcon = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val icon = BitmapDrawable(resources, userIcon)
            val userNames = auth.currentUser?.displayName
            lifecycleScope.launch(Dispatchers.Main) {
                userImage.setImageDrawable(icon)
                userName.text = userNames
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.card_view -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.addToBackStack("toChat")
                    ?.replace(R.id.frag_cont, ChatFragment())
                    ?.commit()
            }
        }
    }
}