package com.example.micro_volunteering.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.ActivityMainBinding
import com.example.micro_volunteering.domain.event.NetworkErrorManager
import com.example.micro_volunteering.domain.model.AppError
import com.example.micro_volunteering.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()
        observeViewModel()
        viewModel.handleNotificationIntent(intent)

        lifecycleScope.launch {
            NetworkErrorManager.errorFlow.collect { error ->
                handleError(error)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        viewModel.handleNotificationIntent(intent)
    }

    fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationEvent.collect { fragmentId ->
                    if (fragmentId != null) {
                        try {
                            navController.navigate(fragmentId)
                        } catch (e: Exception) {
                            Toast.makeText(this@MainActivity, R.string.could_not_open_notification, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavGraph.setupWithNavController(navController)

        binding.bottomNavGraph.setOnItemReselectedListener { item ->
            val reselectedDestinationId = item.itemId
            navController.popBackStack(reselectedDestinationId, false)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authorizationFragment,
                R.id.registrationFragment,
                R.id.roleSelectionFragment,
                R.id.verifyEmailFragment,
                R.id.adminPanelFragment,
                R.id.unverifiedOrganizationFragment -> {
                    binding.bottomNavGraph.isVisible = false
                }
                else -> {
                    binding.bottomNavGraph.isVisible = true
                }
            }
        }
    }

    private fun handleError(error: AppError) {
        val message = when (error) {
            is AppError.NoInternet -> getString(R.string.error_no_internet)
            is AppError.ServerError -> getString(R.string.error_server_message, error.code)
            is AppError.Unknown -> getString(R.string.error_unknown)
            is AppError.ClientError -> error.message ?: getString(R.string.error_code, error.code)
            AppError.Unauthorized -> {
                navigateToAuth()
                getString(R.string.error_session_expired)
            }
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToAuth() {
        navController.navigate(
            R.id.roleSelectionFragment,
            null,
            androidx.navigation.navOptions {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        )
    }
}