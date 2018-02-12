package pl.marcinstramowski.shoppinglist.extensions

import android.app.Activity
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatEditText
import android.view.LayoutInflater
import pl.marcinstramowski.shoppinglist.R

fun Activity.showTextInputDialog(@StringRes titleRes: Int, onTextProvided: (String) -> Unit) {
    AlertDialog.Builder(this)
        .setTitle(getString(titleRes))
        .setView(LayoutInflater.from(this).inflate(R.layout.dialog_add_list, null))
        .setPositiveButton(R.string.ok, { dialog, which ->
            val listNameEditText = (dialog as AlertDialog).findViewById<AppCompatEditText>(R.id.listNameEditText)
            val text = listNameEditText?.text.toString()
            if (text.isNotBlank()) { onTextProvided(text) }
        })
        .setNegativeButton(R.string.cancel, null)
        .create()
        .show()
}