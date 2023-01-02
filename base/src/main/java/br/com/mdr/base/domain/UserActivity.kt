package br.com.mdr.base.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.mdr.base.extension.asBrString
import br.com.mdr.base.extension.getActivityColor
import br.com.mdr.base.extension.getActivityIcon
import java.util.*
import kotlin.math.abs
import kotlin.math.floor

private const val DAY_STR = "dia(s)"
private const val HOUR_STR = "hora(s)"
private const val MINUTE_STR = "minuto(s)"
private const val COMMA = ", "
private const val AND = " e "
private const val ZERO_VALUE = 0L

@Entity
class UserActivity(
    @PrimaryKey
    val key: String,
    val activity: String,
    val type: String,
    val link: String?,
    @TypeConverters(UserStatusConverter::class)
    var status: UserActivityStatus,
    var started: Long = ZERO_VALUE,
    var finished: Long = ZERO_VALUE
): IUserActivity {
    override fun getActivityColor() = type.getActivityColor()
    override fun getActivityIcon() = type.getActivityIcon()

    fun getDate(): String? {
        val stringDate: String? =
            when (status) {
                UserActivityStatus.IN_PROGRESS -> {
                    val date = Date(started)
                    date.asBrString()
                }
                UserActivityStatus.FINISHED -> {
                    getTimeSpent()
                }
                else -> null
            }

        return stringDate
    }

    private fun getTimeSpent(): String {
        var timeSpent = ""

        val secs = floor((abs(finished - started) / 1000).toDouble())
        val minutes = floor(secs / 60)
        val hours = floor(minutes / 60)
        val days = floor(hours / 24)
        if (days > ZERO_VALUE) {
            timeSpent = "${days.toInt()} $DAY_STR$COMMA"
        }

        if (days > ZERO_VALUE || hours > ZERO_VALUE) {
            timeSpent += "${hours.toInt()} $HOUR_STR$AND"
        }
        if (minutes > ZERO_VALUE) {
            timeSpent += "${minutes.toInt()} $MINUTE_STR"
        }

        return timeSpent
    }
}

class UserStatusConverter {
    @TypeConverter
    fun toStatus(value: String) = enumValueOf<UserActivityStatus>(value)

    @TypeConverter
    fun fromStatus(value: UserActivityStatus) = value.status
}