package id.ak.movieshighlight.ext

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.localeWithLocaleIdentifier

actual fun Number.formatLocalNumber(): String {
    val nsNumber = when (this) {
        is Double -> NSNumber(this)
        is Float -> NSNumber(this)
        is Int -> NSNumber(this)
        is Long -> NSNumber(this)
        else -> NSNumber(this.toDouble())
    }

    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        usesGroupingSeparator = true
        maximumFractionDigits = 1u  // Note: NSNumberFormatter uses ULong for digit counts
    }

    return formatter.stringFromNumber(nsNumber) ?: this.toString()
}

actual fun Number.formatCurrency(): String {
    val nsNumber = when (this) {
        is Double -> NSNumber(this)
        is Float -> NSNumber(this)
        is Int -> NSNumber(this)
        is Long -> NSNumber(this)
        else -> NSNumber(this.toDouble())
    }

    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
        locale = NSLocale.localeWithLocaleIdentifier("en_US")
    }

    return formatter.stringFromNumber(nsNumber) ?: this.toString()
}