package com.example.micro_volunteering.presentation.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ResourceProvider {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun formatErrors(errorIds: List<Int>): String? {
        if (errorIds.isEmpty()) {
            return null
        }

        return errorIds.joinToString("\n") { id ->
            "- ${context.getString(id)}"
        }
    }
}