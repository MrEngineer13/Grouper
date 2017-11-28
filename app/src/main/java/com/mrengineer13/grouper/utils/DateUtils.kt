package com.mrengineer13.grouper.utils

import android.text.format.DateUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jon on 11/13/17.
 */

 object DateUtils {

    fun formatMessageTime(time: Long): CharSequence {
        val nowInMillis = Date().time
        val diff = nowInMillis - time
        val secondInMillis = 1000L
        val hourInMillis = secondInMillis * 60 * 60
        return when {
            diff < secondInMillis -> "Now"
            diff > hourInMillis -> {
                val messageFormat = SimpleDateFormat("EEE H:mm a", Locale.getDefault())
                messageFormat.format(Date(time))
            }
            else -> DateUtils.getRelativeTimeSpanString(time, nowInMillis, DateUtils.MINUTE_IN_MILLIS)
        }
    }
}
