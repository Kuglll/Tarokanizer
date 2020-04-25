package com.kusa.tarokanizer.onboarding_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.kusa.tarokanizer.R
import kotlinx.android.synthetic.main.fragment_onboarding_general.*

class OnboardingRoundFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headerTextView.text = HtmlCompat.fromHtml("<b>Izbris posamezne igre:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        headerTextView2.text = "Če se zmotiš pri dodajanju posamezne igre, jo pobrišeš tako, da klikneš na njo."
        image.background = resources.getDrawable(R.drawable.onboarding_image5, null)

    }

}