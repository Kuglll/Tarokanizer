package com.kusa.tarokanizer.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class DialogFactory {

    companion object {
        fun displayObvezenKlopDialog(context: Context) {
            val dialog = AlertDialog.Builder(context).create()
            dialog.setMessage("Nekdo od igralcev je na 0! Obvezen klop!")
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "V REDU", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    dialog.dismiss()
                }
            })
            dialog.show()
        }

    }
}