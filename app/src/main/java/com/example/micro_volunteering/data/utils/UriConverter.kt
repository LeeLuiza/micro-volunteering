package com.example.micro_volunteering.data.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import androidx.core.net.toUri
import com.example.micro_volunteering.data.constants.AuthConstants

class UriConverter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun prepareImageBody(uriString: String): MultipartBody.Part? {
        try {
            val uri = uriString.toUri()

            val filesDir = context.cacheDir
            val tempFile = File.createTempFile(AuthConstants.UPLOAD_IMAGE, AuthConstants.FORMAT_IMAGE, filesDir)

            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            val requestFile = tempFile.asRequestBody(AuthConstants.IMAGE_JPEG.toMediaTypeOrNull())
            return MultipartBody.Part.createFormData(AuthConstants.IMAGE, tempFile.name, requestFile)

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}