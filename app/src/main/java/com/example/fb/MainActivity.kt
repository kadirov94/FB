package com.example.fb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.fb.view.ChatFragment
import com.example.fb.view.ChatListFragment
import com.example.fb.view.SettingFragment
import com.example.fb.vm.ChatViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.frag_cont, ChatListFragment())
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_home -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frag_cont, ChatListFragment()).commit()
                }
            }
            R.id.favorite_favorite -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frag_cont, SettingFragment()).commit()
                }
            }
        }
        return true
    }
}