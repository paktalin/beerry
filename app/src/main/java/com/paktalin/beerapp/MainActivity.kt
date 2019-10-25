package com.paktalin.beerapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        navigation_view.setupWithNavController(navController)


        val sharedPref: SharedPreferences = getSharedPreferences("my_pref", 0)
        val editor = sharedPref.edit()
        editor.putBoolean("test", true)
        editor.apply()
    }

}