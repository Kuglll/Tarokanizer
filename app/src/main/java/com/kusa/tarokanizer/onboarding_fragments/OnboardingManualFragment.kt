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

class OnboardingManualFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val html = "- S klikom na <b>+</b> ročno dodaš točke za odigrano igro.<br><br>" +
            "- S klikom na ime igralca dodaš radlce.<br><br>" +
            "Pri tem načinu gre le za zamenjavo svinčnika in papirja."

        view.findViewById<TextView>(R.id.headerTextView).text = HtmlCompat.fromHtml("<b>Ročni način:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        view.findViewById<TextView>(R.id.headerTextView2).text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

        view.findViewById<ImageView>(R.id.image).background = resources.getDrawable(R.drawable.onboarding_image4, null)

    }

}
