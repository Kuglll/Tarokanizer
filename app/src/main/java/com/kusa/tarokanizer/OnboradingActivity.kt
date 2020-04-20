package com.kusa.tarokanizer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class OnboradingActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_activity)
        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        when {
            firstStart() -> displayOnboarding()
            !firstStart() -> navigateToMainActivity()
        }
    }

    fun firstStart(): Boolean {
        //TODO: uncomment this
        //return !preferences.contains("appStarted")
        return true
    }

    fun displayOnboarding() {
        val editor = preferences.edit()
        editor.putBoolean("appStarted", true)
        editor.apply()

        //TODO: Display onboarding
    }

    fun navigateToMainActivity() {
        startActivity(MainActivity.returnMainActivityIntent(this))
    }
}