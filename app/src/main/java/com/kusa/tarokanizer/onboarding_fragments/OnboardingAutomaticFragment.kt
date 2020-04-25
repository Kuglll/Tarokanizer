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

class OnboardingAutomaticFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val html = "- S klikom na <b>+</b> dodaš igro, izbereš katera igra je bila igrana," +
            " kdo je igral itd. Aplikacija samodejno računa točke in dodaja radlce.<br><br>" +
            "- S klikom na ✔ zaključiš igro, aplikacija odšteje radlce in določi zmagovalca."

        headerTextView.text = HtmlCompat.fromHtml("<b>Avtomatski način:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        headerTextView2.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)

        image.background = resources.getDrawable(R.drawable.onboarding_image2, null)

    }

}