package br.com.mdr.base.extension

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import br.com.mdr.base.R
import br.com.mdr.base.domain.ActivityType

@ColorRes
fun String.getActivityColor() =
    when (this) {
        ActivityType.BUSYWORK.type -> R.color.colorBusy
        ActivityType.CHARITY.type -> R.color.colorCharity
        ActivityType.COOKING.type -> R.color.colorCooking
        ActivityType.DIY.type -> R.color.colorDiy
        ActivityType.EDUCATIONAL.type -> R.color.colorEducational
        ActivityType.MUSIC.type -> R.color.colorMusic
        ActivityType.RECREATIONAL.type -> R.color.colorRecreational
        ActivityType.RELAXATION.type -> R.color.colorRelaxation
        ActivityType.SOCIAL.type -> R.color.colorSocial
        else -> R.color.colorBusy
    }

@DrawableRes
fun String.getActivityIcon() =
    when (this) {
        ActivityType.BUSYWORK.type -> R.drawable.ic_busywork
        ActivityType.CHARITY.type -> R.drawable.ic_charity
        ActivityType.COOKING.type -> R.drawable.ic_cooking
        ActivityType.DIY.type -> R.drawable.ic_diy
        ActivityType.EDUCATIONAL.type -> R.drawable.ic_educational
        ActivityType.MUSIC.type -> R.drawable.ic_music
        ActivityType.RECREATIONAL.type -> R.drawable.ic_recreational
        ActivityType.RELAXATION.type -> R.drawable.ic_relaxation
        ActivityType.SOCIAL.type -> R.drawable.ic_social
        else -> R.drawable.ic_busywork
    }