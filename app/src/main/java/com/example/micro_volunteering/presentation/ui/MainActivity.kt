package com.example.micro_volunteering.presentation.ui

import android.R.id.message
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.micro_volunteering.R
import com.example.micro_volunteering.domain.event.NetworkErrorManager
import com.example.micro_volunteering.domain.model.AppError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            NetworkErrorManager.errorFlow.collect { error ->
                handleError(error)
            }
        }
    }

    private fun handleError(error: AppError) {
        val message = when (error) {
            is AppError.NoInternet -> getString(R.string.error_no_internet)
            is AppError.ServerError -> getString(R.string.error_server_message, error.code)
            is AppError.Unknown -> getString(R.string.error_unknown)
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}