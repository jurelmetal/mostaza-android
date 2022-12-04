package com.juanetoh.mostaza.extensions

import android.content.Context
import android.text.format.DateUtils
import android.text.format.DateUtils.SECOND_IN_MILLIS
import android.text.format.DateUtils.WEEK_IN_MILLIS
import java.time.ZonedDateTime

fun ZonedDateTime.humanReadableDelta(context: Context): String {
    return DateUtils.getRelativeDateTimeString(context, this.toInstant().toEpochMilli(), SECOND_IN_MILLIS, WEEK_IN_MILLIS * 52, 0).toString()
}