package com.juanetoh.mostaza.extensions

import android.content.Context
import android.text.format.DateUtils
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.text.format.DateUtils.YEAR_IN_MILLIS
import androidx.annotation.PluralsRes
import com.juanetoh.mostaza.R
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

fun ZonedDateTime.humanReadableDelta(context: Context): String {
    return DateUtils.getRelativeDateTimeString(context, this.toInstant().toEpochMilli(), SECOND_IN_MILLIS, YEAR_IN_MILLIS, 0).toString()
}