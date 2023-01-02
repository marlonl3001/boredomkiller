package br.com.mdr.base.domain

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import br.com.mdr.base.R

interface IUserActivity {
    @ColorRes
    fun getActivityColor(): Int

    @DrawableRes
    fun getActivityIcon(): Int
}