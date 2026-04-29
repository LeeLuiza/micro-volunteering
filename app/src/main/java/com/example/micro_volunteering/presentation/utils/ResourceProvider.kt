package com.example.micro_volunteering.presentation.utils

interface ResourceProvider {
    fun getString(resId: Int): String
    fun formatErrors(errorIds: List<Int>): String
}