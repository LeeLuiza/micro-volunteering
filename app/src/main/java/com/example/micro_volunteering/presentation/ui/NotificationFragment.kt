package com.example.micro_volunteering.presentation.ui

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.micro_volunteering.databinding.FragmentNotificationBinding
import com.example.micro_volunteering.presentation.adapter.NotificationAdapter
import com.example.micro_volunteering.presentation.viewmodel.NotificationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(FragmentNotificationBinding::inflate) {

    override val viewModel: NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun setupViews() {
        setupRecyclerView()
    }

    override fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
        }

        viewModel.notifications.observe(viewLifecycleOwner) { notification ->
            binding.progressBar.isVisible = false
            binding.content.isVisible = !notification.isEmpty()
            adapter.updateNotification(notification)
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }
    }

    private fun setupRecyclerView() {
        val isOrganization = viewModel.isUserOrganization()

        adapter = NotificationAdapter(
            isOrganization = isOrganization,
            onItemClick = { userId ->

            },
            onAcceptClick = { idTask, idVolunteer ->
                viewModel.acceptVolunteer(idTask, idVolunteer)
            },
            onDismissClick = { idTask, idVolunteer ->
                viewModel.dismissVolunteer(idTask, idVolunteer)
            }
        )

        binding.recyclerViewNotification.adapter = adapter
    }
}