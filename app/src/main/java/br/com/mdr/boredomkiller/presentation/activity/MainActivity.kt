package br.com.mdr.boredomkiller.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.mdr.boredomkiller.R
import br.com.mdr.boredomkiller.databinding.ActivityMainBinding
import br.com.mdr.boredomkiller.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private val mainViewModel: MainViewModel by viewModel()
    private val delayTime = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleSplash()
    }

    private fun setupNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNavigation, navHostFragment.navController)
    }

    private fun handleSplash() {
        binding.bottomNavigation.visibility = View.GONE
        binding.loading = false
        Handler(Looper.getMainLooper())
            .postDelayed({
                setupNavigation()
                setupViewModel()
                navHostFragment.navController.navigate(R.id.action_splashFragment_to_home)
                binding.bottomNavigation.visibility = View.VISIBLE
            }, delayTime)
    }

    private fun setupViewModel() {
        with(mainViewModel) {
            loading.observe(this@MainActivity) {
                binding.loading = it
            }
            apiError.observe(this@MainActivity) {
                binding.apiError = it
            }
        }
    }
}