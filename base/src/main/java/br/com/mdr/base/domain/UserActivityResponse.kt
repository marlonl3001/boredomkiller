package br.com.mdr.base.domain

import br.com.mdr.base.extension.getActivityColor
import br.com.mdr.base.extension.getActivityIcon

data class UserActivityResponse(
    val key: String,
    val activity: String,
    val type: String,
    val link: String?
): IUserActivity {
    override fun getActivityColor() = type.getActivityColor()
    override fun getActivityIcon() = type.getActivityIcon()
}
