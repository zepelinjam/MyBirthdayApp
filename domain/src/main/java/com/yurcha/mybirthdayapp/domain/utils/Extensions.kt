package com.yurcha.mybirthdayapp.domain.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd.MM.yyyy"

fun Long?.toDateFormat(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return if (this != null) sdf.format(this) else ""
}