package id.ak.movieshighlight.ext

import java.text.NumberFormat
import java.util.Locale

actual fun Number.formatLocalNumber(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 1
    }
    return formatter.format(this)
}

actual fun Number.formatCurrency(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
    return formatter.format(this)
}