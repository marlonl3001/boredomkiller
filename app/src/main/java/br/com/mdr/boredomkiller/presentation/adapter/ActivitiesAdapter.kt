package br.com.mdr.boredomkiller.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.mdr.base.domain.UserActivity
import br.com.mdr.base.domain.UserActivityStatus
import br.com.mdr.boredomkiller.R
import br.com.mdr.boredomkiller.databinding.UserActivityItemBinding

class ActivitiesAdapter(
    private val onActivityClick: (activity: UserActivity) -> Unit,
    private val onGiveUpClick: (activity: UserActivity) -> Unit
):
    ListAdapter<UserActivity, ActivitiesAdapter.ActivitiesViewHolder>(UserActivitiesCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserActivityItemBinding.inflate(inflater, parent, false)

        return ActivitiesViewHolder(binding, onActivityClick, onGiveUpClick)
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ActivitiesViewHolder(private val binding: UserActivityItemBinding,
                               private val onActivityClick: (userActivity: UserActivity) -> Unit,
                               private val onGiveUpClick: (activity: UserActivity) -> Unit):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: UserActivity) {
            binding.apply {
                userActivity = activity
                val buttonText =
                    if (activity.status == UserActivityStatus.IN_PROGRESS)
                        R.string.finish_activity
                    else
                        R.string.start_activity

                with(btnAction) {
                    text =
                        root.context.resources.getString(buttonText)

                    visibility =
                        if (activity.status == UserActivityStatus.IN_PROGRESS ||
                            activity.status == UserActivityStatus.ADDED)
                            View.VISIBLE
                        else
                            View.GONE

                    val buttonIcon =
                        if (activity.status == UserActivityStatus.IN_PROGRESS)
                            R.drawable.ic_check
                        else
                            R.drawable.ic_start

                    setIconResource(buttonIcon)

                    setOnClickListener {
                        onActivityClick.invoke(activity)
                    }
                }

                btnGiveUp.visibility =
                    if (activity.status == UserActivityStatus.IN_PROGRESS)
                        View.VISIBLE
                    else
                        View.GONE
                btnGiveUp.setOnClickListener {
                    onGiveUpClick.invoke(activity)
                }
            }
        }
    }

    private class UserActivitiesCallback : DiffUtil.ItemCallback<UserActivity>() {
        override fun areItemsTheSame(oldItem: UserActivity, newItem: UserActivity) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: UserActivity, newItem: UserActivity) =
            oldItem.key == newItem.key
    }
}