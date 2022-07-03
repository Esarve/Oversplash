package com.sourav.oversplash.utils


import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.sourav.oversplash.R


class DialogHelper {

    companion object{
        lateinit var dialog: AlertDialog

        private fun showSimpleAlertDialog(context: Context?, title: String?, message: String?) {
            val builder = AlertDialog.Builder(
                context!!
            )
            builder.setView(R.layout.dialog_loading)
            val dialog = builder.create()
            dialog.show()
        }
    }

}