package com.kusa.tarokanizer.onboarding_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kusa.tarokanizer.R
import kotlinx.android.synthetic.main.fragment_onboarding_general.*

class OnboardingAutomaticFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headerTextView.text = "Avtomatski način: \n S klikom na + dodaš igro, izbereš katerga igra je bila igrana," +
            " kdo je igral itd. Aplikacija samodejno računa točke in dodaja radlce\n" +
            " S klikom na ✔ zaključiš igro, aplikacija odšteje radlce in naznani zmagovalca"
        image.background = resources.getDrawable(R.drawable.onboarding_image2, null)

    }

}