package com.example.fb.view

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.fb.AuthActivity
import com.example.fb.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingFragment : Fragment(), View.OnClickListener {
    private lateinit var signOutButton: TextView
    private lateinit var exit: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = layoutInflater.inflate(R.layout.fragment_setting, container, false)
        auth = Firebase.auth
        getUserData()
        signOutButton = view.findViewById(R.id.sign_out)
        signOutButton.setOnClickListener(this)
        exit = view.findViewById(R.id.out)
        exit.setOnClickListener(this)
        imageView = view.findViewById(R.id.user_ava)
        textView = view.findViewById(R.id.user_name)
        return view
    }

    private fun getUserData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val userIcon = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val icon = BitmapDrawable(resources, userIcon)
            val userName = auth.currentUser?.displayName
            lifecycleScope.launch(Dispatchers.Main) {
                imageView.setImageDrawable(icon)
                textView.text = userName
            }
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.sign_out -> {
                auth.signOut()
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
            R.id.out -> {
                activity?.finish()
            }
        }
    }
}