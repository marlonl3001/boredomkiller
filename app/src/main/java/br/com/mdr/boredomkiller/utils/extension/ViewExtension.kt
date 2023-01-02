package br.com.mdr.boredomkiller.utils.extension

import android.view.View
import androidx.core.content.ContextCompat
import br.com.mdr.boredomkiller.R
import com.google.android.material.snackbar.Snackbar

fun View.successSnack(message: Int) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackBar.setBackgroundTint(ContextCompat.getColor(this.context, R.color.colorSuccess))
    snackBar.show()
}