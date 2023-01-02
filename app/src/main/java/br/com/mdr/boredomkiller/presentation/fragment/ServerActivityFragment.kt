package br.com.mdr.boredomkiller.presentation.fragment

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.mdr.base.domain.UserActivityResponse
import br.com.mdr.boredomkiller.R
import br.com.mdr.boredomkiller.databinding.FragmentServerActivityBinding
import br.com.mdr.boredomkiller.presentation.viewmodel.MainViewModel
import br.com.mdr.boredomkiller.utils.extension.showBottomSheet
import br.com.mdr.boredomkiller.utils.extension.successSnack
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

private const val CAMERA_DISTANCE = 3000

class ServerActivityFragment : Fragment() {
    private var binding: FragmentServerActivityBinding? = null
    private val viewModel: MainViewModel by sharedViewModel()

    private lateinit var frontAnim: AnimatorSet
    private lateinit var backAnim: AnimatorSet
    private var isFront = true
    private var canAnimate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServerActivityBinding.inflate(inflater)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupCards()
        setupViewModel()
        setupListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                openSortBottomSheet()
            }
        }
        return true
    }

    private fun setupToolbar() {
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding?.toolbar)
    }

    private fun openSortBottomSheet() {
        val bottomSheet = SortBottomSheetFragment().apply {
            itemClick = { sortItem ->
                canAnimate = true
                viewModel.sortActivities(sortItem)
            }
        }
        showBottomSheet(bottomSheet)
    }

    private fun setupCards() {
        val context = requireContext()
        val scale = context.resources.displayMetrics.density
        val cameraDistance = scale * CAMERA_DISTANCE
        binding?.apply {
            cardFront.cameraDistance = cameraDistance
            cardBack.cameraDistance = cameraDistance
            loading = true
        }

        frontAnim =
            AnimatorInflater.loadAnimator(context, R.animator.front_animator) as AnimatorSet
        backAnim =
            AnimatorInflater.loadAnimator(context, R.animator.back_animator) as AnimatorSet
    }

    private fun setupViewModel() {
        with(viewModel) {
            userActivity.observe(viewLifecycleOwner) {
                animateCards(it)
            }

            loading.observe(viewLifecycleOwner) {
                if (canAnimate.not())
                    binding?.loading = it
            }

            successMessage.observe(viewLifecycleOwner) {
                binding?.root?.successSnack(it)
            }

            activityType.observe(viewLifecycleOwner) {
                binding?.filter = String.format(
                    getString(R.string.activities_suggestion), it.type
                )
            }

            fetchActivity()
        }
    }

    private fun animateCards(userActivity: UserActivityResponse) {
        binding?.apply {
            isFront = if (isFront) {
                frontUserActivity = userActivity
                frontAnim.setTarget(cardBack)
                backAnim.setTarget(cardFront)
                if (canAnimate) {
                    frontAnim.start()
                    backAnim.start()
                }
                false
            } else {
                backUserActivity = userActivity
                frontAnim.setTarget(cardFront)
                backAnim.setTarget(cardBack)
                if (canAnimate) {
                    backAnim.start()
                    frontAnim.start()
                }
                true
            }
        }
        canAnimate = false
    }

    private fun setupListeners() {
        binding?.apply {
            btnStart.setOnClickListener(onStartClickListener())
            btnBackStart.setOnClickListener(onStartClickListener())
            btnAddToFavorites.setOnClickListener {
                addToFavorites()
            }
            btnBackAddFavorites.setOnClickListener {
                addToFavorites()
            }
            btnChange.setOnClickListener(onChangeClickListener())
        }
    }

    private fun onStartClickListener() = View.OnClickListener {
        canAnimate = true
        viewModel.addActivity(true)
    }

    private fun addToFavorites() {
        canAnimate = true
        viewModel.addActivity()
    }

    private fun onChangeClickListener() = View.OnClickListener {
        canAnimate = true
        viewModel.fetchActivity()
    }
}