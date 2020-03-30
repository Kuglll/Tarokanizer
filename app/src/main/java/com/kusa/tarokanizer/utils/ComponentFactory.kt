package com.kusa.tarokanizer.utils

import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kusa.tarokanizer.R
import com.kusa.tarokanizer.TarockanizerApp

class ComponentFactory{

    companion object{

        fun createTextViewPlayer(
            player: String?,
            automatic: Boolean,
            onClick: ((Int) -> Unit)?
        ): TextView? {
            val context = TarockanizerApp.instance

            val textView = TextView(context)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36f)
            textView.text = player
            textView.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            textView.gravity = Gravity.CENTER
            textView.setBackgroundResource(R.drawable.border)
            if (!automatic) {
                textView.setOnClickListener {
                    val ll = it.parent as LinearLayout
                    onClick!!(ll.indexOfChild(it))
                }
            }
            return textView
        }

        fun createPlayersRadlcLayout(i: Int): LinearLayout? {
            val context = TarockanizerApp.instance

            val ll = LinearLayout(context)
            ll.id = i
            ll.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50, 1f)
            ll.gravity = Gravity.CENTER_HORIZONTAL
            ll.setBackgroundResource(R.drawable.border)
            return ll
        }

        fun createRadlc(): ImageView? {
            val context = TarockanizerApp.instance
            val radlc = ImageView(context)
            radlc.layoutParams = LinearLayout.LayoutParams(40, 40)
            radlc.setPadding(5, 10, 5, 0)
            radlc.setImageResource(R.color.colorAccent)
            return radlc
        }

        fun createScoreLayout(id: Int): LinearLayout? {
            val context = TarockanizerApp.instance
            val ll = LinearLayout(context)
            ll.id = id
            ll.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            ll.gravity = Gravity.CENTER_HORIZONTAL
            ll.orientation = LinearLayout.VERTICAL
            ll.setBackgroundResource(R.drawable.border)
            return ll
        }

        fun createTextViewScore(score: String?, onClick: (Int) -> Unit, played: Boolean): TextView? {
            val context = TarockanizerApp.instance
            val textView = TextView(context)
            textView.text = score
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            textView.gravity = Gravity.CENTER_HORIZONTAL
            textView.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
            textView.setOnClickListener {
                val ll = it.parent as LinearLayout
                onClick(ll.indexOfChild(it))
            }
            if (played) {
                textView.setBackgroundResource(R.color.greenTransparent)
            }
            return textView
        }

        fun createTextViewSum(id: Int): TextView? {
            val context = TarockanizerApp.instance
            val textView = TextView(context)
            textView.text = "0"
            textView.id = id
            textView.setTextColor(ContextCompat.getColor(context, R.color.brightGray))
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
            textView.typeface = Typeface.DEFAULT_BOLD
            textView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            textView.gravity = Gravity.CENTER_HORIZONTAL
            textView.setBackgroundResource(R.drawable.border2)
            return textView
        }

    }

}