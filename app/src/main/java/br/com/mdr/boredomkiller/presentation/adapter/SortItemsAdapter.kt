package br.com.mdr.boredomkiller.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.mdr.base.domain.ActivityType
import br.com.mdr.base.extension.getActivityColor
import br.com.mdr.base.extension.getActivityIcon
import br.com.mdr.boredomkiller.databinding.BottomSheetSortItemBinding

class SortItemsAdapter(
    private val sortItems: List<ActivityType>,
    private val onSortClick: (sort: ActivityType) -> Unit
): RecyclerView.Adapter<SortItemsAdapter.SortItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BottomSheetSortItemBinding.inflate(inflater, parent, false)

        return SortItemsViewHolder(binding, onSortClick)
    }

    override fun onBindViewHolder(holder: SortItemsViewHolder, position: Int) {
        holder.bind(sortItems[position])
    }

    override fun getItemCount() = sortItems.size

    class SortItemsViewHolder(val binding: BottomSheetSortItemBinding,
                           private val onSortClick: (ActivityType: ActivityType) -> Unit):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ActivityType: ActivityType) {
            binding.apply {
                with(ActivityType.type) {
                    txtSort.text = this
                    txtSort.setCompoundDrawablesWithIntrinsicBounds(
                        this.getActivityIcon(), 0, 0, 0)
                }
                root.setBackgroundColor(ContextCompat.getColor(
                    root.context, ActivityType.type.getActivityColor()
                ))
                root.setOnClickListener {
                    onSortClick.invoke(ActivityType)
                }
            }
        }
    }
}