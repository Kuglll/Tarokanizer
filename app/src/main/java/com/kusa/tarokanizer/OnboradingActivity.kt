package com.kusa.tarokanizer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kusa.tarokanizer.onboarding_fragments.OnboardingAutomaticFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingManualFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingModesFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingRoundFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingSettingsFragment
import com.kusa.tarokanizer.onboarding_fragments.OnboardingWelcomeFragment
import com.kusa.tarokanizer.utils.OnboardingViewPagerAdapter
import kotlinx.android.synthetic.main.onboarding_activity.*

class OnboradingActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_activity)
        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)

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
        onboardingViewPager.adapter = OnboardingViewPagerAdapter(supportFragmentManager).apply {
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
            onboardingViewPager.adapter?.count?.let { length ->
                if (onboardingViewPager.currentItem == length - 1) {
                    navigateToMainActivity()
                } else {
                    onboardingViewPager.currentItem++
                }
            }
        }
    }
}