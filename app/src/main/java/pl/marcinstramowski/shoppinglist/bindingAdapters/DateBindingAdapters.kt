package pl.marcinstramowski.shoppinglist.bindingAdapters

import android.databinding.BindingAdapter
import android.widget.TextView
import java.text.DateFormat
import java.util.*

@BindingAdapter("formattedDate")
fun setImageUrl(textView: TextView, date: Date?) {
    textView.text = DateFormat.getDateTimeInstance(
        DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault()
    ).format(date)
}