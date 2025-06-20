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

class OnboardingRoundFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.headerTextView).text = HtmlCompat.fromHtml("<b>Izbris posamezne igre:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        view.findViewById<TextView>(R.id.headerTextView2).text = "Če se zmotiš pri dodajanju posamezne igre, jo lahko s klikom na njo pobrišeš."
        view.findViewById<ImageView>(R.id.image).background = resources.getDrawable(R.drawable.onboarding_image5, null)

    }

}
