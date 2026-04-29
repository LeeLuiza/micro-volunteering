package com.example.micro_volunteering.presentation.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentVerifyEmailBinding
import com.example.micro_volunteering.presentation.utils.navigate
import com.example.micro_volunteering.presentation.viewmodel.VerifyEmailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyEmailFragment : BaseFragment<FragmentVerifyEmailBinding, VerifyEmailViewModel>(
    FragmentVerifyEmailBinding::inflate
) {

    override val viewModel: VerifyEmailViewModel by viewModels()
    private val args: VerifyEmailFragmentArgs by navArgs()

    override fun setupViews() {
        setupListener(args.email)
    }

    private fun setupListener(email: String) {
        val fields = listOf(binding.input1, binding.input2, binding.input3, binding.input4)

        fields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    if (!p0.isNullOrEmpty() && p0.length == 1) {
                        if (index < fields.size - 1) {
                            fields[index + 1].requestFocus()
                        }
                    }
                    else if (p0.isNullOrEmpty() && index > 0) {
                        fields[index - 1].requestFocus()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            })
        }

        binding.enter.setOnClickListener {
            val code = fields.joinToString("") { it.text.toString() }
            viewModel.verifyEmail(email, code)
        }

        binding.btnRepeat.setOnClickListener {
            viewModel.resendCode(email)
        }
    }

    override fun observeViewModel() {
        collectFlow(viewModel.isLoading) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.content.isVisible = !isLoading
            binding.enter.isVisible = !isLoading
        }

        collectFlow(viewModel.isSuccess) { isSuccess ->
            if (isSuccess) {
                navigate(VerifyEmailFragmentDirections.actionVerifyEmailFragmentToTaskListFragment())
            }
        }

        collectFlow(viewModel.errorText) { errorText ->
            errorText?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        collectFlow(viewModel.timerText) { textId ->
            binding.btnRepeat.text = getString(textId)
        }

        collectFlow(viewModel.timerSeconds) { seconds ->
            binding.btnRepeat.text = getString(R.string.resend_timer, seconds)
        }

        collectFlow(viewModel.isResendEnabled) { isEnabled ->
            binding.btnRepeat.isEnabled = isEnabled

            if (isEnabled) {
                binding.btnRepeat.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_primary))
            } else {
                binding.btnRepeat.setTextColor(ContextCompat.getColor(requireContext(), R.color.item_background_click))
            }
        }
    }
}