package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.micro_volunteering.R
import com.example.micro_volunteering.databinding.FragmentVerifyEmailBinding
import com.example.micro_volunteering.presentation.extensions.navigate
import com.example.micro_volunteering.presentation.viewmodel.VerifyEmailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerifyEmailFragment : Fragment() {

    private lateinit var binding: FragmentVerifyEmailBinding
    private val viewModel: VerifyEmailViewModel by viewModels()

    private val args: VerifyEmailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyEmailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = args.email

        observeViewModel()
        setupListener(email)
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
            if (fields.all { it.text?.length == 1 }) {
                val code = fields.joinToString("") { it.text.toString() }
                viewModel.verifyEmail(email, code)
            } else {
                Toast.makeText(requireContext(), R.string.error_input_code, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRepeat.setOnClickListener {
            viewModel.repeatCode(email)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.isVisible = true
                binding.content.isVisible = false
                binding.enter.isVisible = false
            }
            else {
                binding.progressBar.isVisible = false
                binding.content.isVisible = true
                binding.enter.isVisible = true
            }
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val action = VerifyEmailFragmentDirections.actionVerifyEmailFragmentToTaskListFragment()
                navigate(action)
            }
        }

        viewModel.timerText.observe(viewLifecycleOwner) { textId ->
            binding.btnRepeat.text = getString(textId)
        }

        viewModel.timerSeconds.observe(viewLifecycleOwner) { seconds ->
            binding.btnRepeat.text = getString(R.string.resend_timer, seconds)
        }

        viewModel.isResendEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.btnRepeat.isEnabled = isEnabled

            if (isEnabled) {
                binding.btnRepeat.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_primary))
            } else {
                binding.btnRepeat.setTextColor(ContextCompat.getColor(requireContext(), R.color.item_background_click))
            }
        }
    }
}