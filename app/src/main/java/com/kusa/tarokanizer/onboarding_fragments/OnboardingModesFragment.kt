package com.kusa.tarokanizer.onboarding_fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.kusa.tarokanizer.R
import kotlinx.android.synthetic.main.fragment_onboarding_general.*

class OnboardingModesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val html = "Aplikacija ima 2 načina delovanja:<br> - <b>Avtomatski</b> <br> - <b>Ročni</b>"

        headerTextView.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        headerTextView2.text = "Med njima lahko kadarkoli preklapljaš v nastavitvah."
        image.background = resources.getDrawable(R.drawable.onboarding_image1, null)

    }

}