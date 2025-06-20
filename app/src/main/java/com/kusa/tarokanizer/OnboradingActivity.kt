package com.kusa.tarokanizer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.kusa.tarokanizer.onboarding_fragments.OnboardingAutomaticFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingManualFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingModesFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingRoundFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingSettingsFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingWelcomeFragment
import com.kusa.tarokanizer.utils.OnboardingViewPagerAdapter

class OnboradingActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    lateinit var viewPager: ViewPager
    lateinit var skipButton: Button
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_activity)
        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

        skipButton = findViewById(R.id.skipButton)
        nextButton = findViewById(R.id.nextButton)

        when {
            !firstStart() -> navigateToMainActivity()
        }
        saveToSharedPrefs()
        initViewPager()
        initListeners()
    }

    fun firstStart(): Boolean {
        return !preferences.contains("appStarted")
    }

    fun navigateToMainActivity() {
        startActivity(MainActivity.returnMainActivityIntent(this))
        finish()
    }

    fun saveToSharedPrefs() {
        val editor = preferences.edit()
        editor.putBoolean("appStarted", true)
        editor.apply()
    }

    fun initViewPager() {
        viewPager = findViewById(R.id.onboardingViewPager)
        viewPager.adapter = OnboardingViewPagerAdapter(supportFragmentManager).apply {
            addFragment(OnboardingWelcomeFragment())
            addFragment(OnboardingModesFragment())
            addFragment(OnboardingAutomaticFragment())
            addFragment(OnboardingSettingsFragment())
            addFragment(OnboardingManualFragment())
            addFragment(OnboardingRoundFragment())
        }
    }

    fun initListeners() {
        skipButton.setOnClickListener {
            navigateToMainActivity()
        }
        nextButton.setOnClickListener {
            viewPager.adapter?.count?.let { length ->
                if (viewPager.currentItem == length - 1) {
                    navigateToMainActivity()
                } else {
                    viewPager.currentItem++
                }
            }
        }
    }
}
