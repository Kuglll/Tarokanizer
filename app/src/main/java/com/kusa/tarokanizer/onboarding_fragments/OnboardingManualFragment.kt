package com.kusa.tarokanizer.onboarding_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kusa.tarokanizer.R
import kotlinx.android.synthetic.main.fragment_onboarding_general.*

class OnboardingManualFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_onboarding_general, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headerTextView.text = "Ročni način: \n" +
            "S klikom na + ročno dodaš točke za odigrano igro \n" +
            "S klikom na ime igralca dodaš radlce \n" +
            "Pri tem načinu gre le za zamenjavo svinčnika in papirja"
        image.background = resources.getDrawable(R.drawable.onboarding_image4, null)

    }

}