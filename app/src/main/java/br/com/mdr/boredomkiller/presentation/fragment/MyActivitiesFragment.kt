package br.com.mdr.boredomkiller.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mdr.base.domain.UserActivityStatus
import br.com.mdr.boredomkiller.databinding.FragmentMyActivitiesBinding
import br.com.mdr.boredomkiller.presentation.adapter.ActivitiesAdapter
import br.com.mdr.boredomkiller.presentation.viewmodel.MainViewModel
import br.com.mdr.boredomkiller.utils.SpacesItemDecoration
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MyActivitiesFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private var binding: FragmentMyActivitiesBinding? = null

    private lateinit var adapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyActivitiesBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        with(viewModel) {
            viewModel.activities.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            getAllActivities()
        }
    }

    private fun setupRecyclerView() {
        adapter = ActivitiesAdapter(
            onActivityClick = {
                when(it.status) {
                    UserActivityStatus.ADDED -> viewModel.startActivity(it)
                    UserActivityStatus.IN_PROGRESS -> viewModel.finishActivity(it, true)
                    else -> Unit
                }
            },
            onGiveUpClick = {
                viewModel.giveUpActivity(it, true)
            }
        )

        binding?.adapter = adapter
        binding?.recyclerActivities?.addItemDecoration(
            SpacesItemDecoration(orientation = GridLayoutManager.VERTICAL))
    }
}