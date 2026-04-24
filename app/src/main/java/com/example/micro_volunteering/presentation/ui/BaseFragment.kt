package com.example.micro_volunteering.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<ViewBindingType : ViewBinding, ViewModelType : ViewModel>(
    private val inflate: Inflate<ViewBindingType>
) : Fragment() {

    private var _binding: ViewBindingType? = null
    protected val binding: ViewBindingType
        get() = _binding!!

    protected abstract val viewModel: ViewModelType

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeViewModel()
        loadData()
    }

    protected open fun setupViews() {}

    protected open fun observeViewModel() {}

    protected open fun loadData() {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}