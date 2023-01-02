package br.com.mdr.boredomkiller.utils.extension

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val BOTTOM_SHEET_FLAG = "BOTTOM_SHEET_FLAG"

fun Fragment.showBottomSheet(bottomSheet: BottomSheetDialogFragment) {
    activity?.supportFragmentManager?.let {
        bottomSheet.show(it, BOTTOM_SHEET_FLAG)
    }
}