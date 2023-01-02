package br.com.mdr.base.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.mdr.base.extension.*
import java.util.*

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

    private fun getTimeSpent() =
        (finished - started).toSpacedHoursMinutesAndSeconds()
}

class UserStatusConverter {
    @TypeConverter
    fun toStatus(value: String) = enumValueOf<UserActivityStatus>(value)

    @TypeConverter
    fun fromStatus(value: UserActivityStatus) = value.status
}