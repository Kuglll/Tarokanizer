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

class OnboardingAutomaticFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val html = "- S klikom na <b>+</b> dodaš igro, izbereš katera igra je bila igrana," +
            " kdo je igral itd. Aplikacija samodejno računa točke in dodaja radlce.<br><br>" +
            "- S klikom na ✔ zaključiš igro, aplikacija odšteje radlce in določi zmagovalca."

        view.findViewById<TextView>(R.id.headerTextView).text = HtmlCompat.fromHtml("<b>Avtomatski način:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        view.findViewById<TextView>(R.id.headerTextView2).text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

        view.findViewById<ImageView>(R.id.image).background = resources.getDrawable(R.drawable.onboarding_image2, null)

    }

}
