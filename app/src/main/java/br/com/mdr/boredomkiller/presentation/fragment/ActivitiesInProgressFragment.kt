package br.com.mdr.boredomkiller.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.com.mdr.boredomkiller.databinding.FragmentActivitiesInProgressBinding
import br.com.mdr.boredomkiller.presentation.adapter.ActivitiesAdapter
import br.com.mdr.boredomkiller.presentation.viewmodel.MainViewModel
import br.com.mdr.boredomkiller.utils.SpacesItemDecoration
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ActivitiesInProgressFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()
    private var binding: FragmentActivitiesInProgressBinding? = null

    private lateinit var adapter: ActivitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActivitiesInProgressBinding.inflate(inflater)
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

            getActivitiesInProgress()
        }
    }

    private fun setupRecyclerView() {
        adapter = ActivitiesAdapter(
            onActivityClick = {
                viewModel.finishActivity(it)
            },
            onGiveUpClick = {
                viewModel.giveUpActivity(it)
            }
        )

        binding?.adapter = adapter
        binding?.recyclerActivities?.addItemDecoration(
            SpacesItemDecoration(orientation = GridLayoutManager.VERTICAL))
    }
}