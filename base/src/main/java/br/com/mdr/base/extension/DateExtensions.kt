package br.com.mdr.base.extension

import java.text.SimpleDateFormat
import java.util.*

const val BR_FULL_DATETIME_FORMAT = "dd/MM/yyyy 'às' HH:mm:ss"

object BRDateFormatter : SimpleDateFormat(BR_FULL_DATETIME_FORMAT, Locale.getDefault())

fun Date.asBrString(): String = BRDateFormatter.format(this)