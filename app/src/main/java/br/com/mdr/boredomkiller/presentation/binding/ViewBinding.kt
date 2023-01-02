package br.com.mdr.boredomkiller.presentation.binding

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import br.com.mdr.base.R
import com.google.android.material.snackbar.Snackbar

object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        text?.let {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }
    }

    @JvmStatic
    @BindingAdapter("imageSrc")
    fun ImageView.bindDrawable(@DrawableRes id: Int) {
        this.setImageResource(id)
    }

    @JvmStatic
    @BindingAdapter("colorSrc")
    fun bindCardBackgroundColor(view: CardView, @ColorRes id: Int) {
        view.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(view.context, if (id == 0) R.color.colorBusy else id))
    }

    @JvmStatic
    @BindingAdapter("gone")
    fun bindGone(view: View, shouldBeGone: Boolean) {
        view.visibility = if (shouldBeGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}
