package com.covergenius.mca_sdk_android.domain.use_case

import android.content.ContentResolver
import android.net.Uri
import com.covergenius.mca_sdk_android.domain.repo.InitiatePurchaseRepo
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class FileUploadUseCase @Inject constructor(val repo: InitiatePurchaseRepo) {
    private val SCOPE = "FILE_UPLOAD_USE_CASE"

    operator fun invoke(token: String, file: Uri, contentResolver: ContentResolver): String? {
        var uploadUrl: String? = null
        runBlocking {
            try {
                val response = repo.uploadFile(token, file, contentResolver)
                uploadUrl = (response["data"] as Map<String, Any>)["file_url"].toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return uploadUrl
    }
}