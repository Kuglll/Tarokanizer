package com.kusa.tarokanizer.onboarding_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.kusa.tarokanizer.R

class OnboardingModesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val html = "Aplikacija ima 2 načina delovanja:<br> - <b>Avtomatski</b> <br> - <b>Ročni</b>"

        view.findViewById<TextView>(R.id.headerTextView).text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        view.findViewById<TextView>(R.id.headerTextView2).text = "Med njima lahko kadarkoli preklapljaš v nastavitvah."
        view.findViewById<ImageView>(R.id.image).background = resources.getDrawable(R.drawable.onboarding_image1, null)

    }

}
