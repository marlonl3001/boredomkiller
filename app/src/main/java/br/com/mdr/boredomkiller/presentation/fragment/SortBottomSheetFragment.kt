package br.com.mdr.boredomkiller.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mdr.base.domain.ActivityType
import br.com.mdr.boredomkiller.databinding.FragmentSortBottomSheetBinding
import br.com.mdr.boredomkiller.presentation.adapter.SortItemsAdapter
import br.com.mdr.boredomkiller.utils.SpacesItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private val sortList = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

class SortBottomSheetFragment: BottomSheetDialogFragment() {

    private var binding: FragmentSortBottomSheetBinding? = null
    var itemClick: ((ActivityType) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSortBottomSheetBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding?.apply {
            recyclerSort.addItemDecoration(
                SpacesItemDecoration(orientation = GridLayoutManager.VERTICAL)
            )
            recyclerSort.adapter = SortItemsAdapter(getSortItems()) {
                itemClick?.invoke(it)
                dismiss()
            }
        }
    }

    private fun getSortItems(): List<ActivityType> {
        val sortItems = mutableListOf<ActivityType>()

        for (index in sortList) {
            val sortItem =
                when (index) {
                    0 -> ActivityType.RANDOM
                    1 -> ActivityType.BUSYWORK
                    2 -> ActivityType.CHARITY
                    3 -> ActivityType.COOKING
                    4 -> ActivityType.DIY
                    5 -> ActivityType.EDUCATIONAL
                    6 -> ActivityType.MUSIC
                    7 -> ActivityType.RECREATIONAL
                    8 -> ActivityType.RELAXATION
                    9 -> ActivityType.SOCIAL
                    else -> ActivityType.BUSYWORK
                }

            sortItems.add(sortItem)
        }

        return sortItems
    }

}