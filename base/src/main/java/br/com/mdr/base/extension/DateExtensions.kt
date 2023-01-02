package br.com.mdr.base.extension

import java.text.SimpleDateFormat
import java.util.*

const val BR_FULL_DATETIME_FORMAT = "dd/MM/yyyy 'às' HH:mm:ss"

private const val MILLISECOND_TO_SECOND = 1000
private const val MILLISECOND_TO_MINUTE = MILLISECOND_TO_SECOND * 60
private const val MILLISECOND_TO_HOUR = MILLISECOND_TO_MINUTE * 60
private const val MINUTES_TO_SECONDS = 60
private const val HOURS_OF_DAY = 24

object BRDateFormatter : SimpleDateFormat(BR_FULL_DATETIME_FORMAT, Locale.getDefault())

fun Date.asBrString(): String = BRDateFormatter.format(this)

fun Long.toSpacedHoursMinutesAndSeconds(): String {
    val hours = this.convertToHours() % HOURS_OF_DAY
    val minutes = this.convertToMinutes() % MINUTES_TO_SECONDS
    val seconds = this.convertToRemainingSeconds()

    return String.format("%dh %02dm %02ds", hours, minutes, seconds)
}

fun Long.convertToHours(): Long = this / MILLISECOND_TO_HOUR
fun Long.convertToMinutes(): Long = this / MILLISECOND_TO_MINUTE
fun Long.convertToRemainingSeconds(): Long = (this / MILLISECOND_TO_SECOND) % MINUTES_TO_SECONDS