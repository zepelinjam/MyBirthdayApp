package com.yurcha.mybirthdayapp.presentation.ui.common

import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.yurcha.mybirthdayapp.presentation.R
import java.io.File

/**
 * Dialog for to pick from gallery or take a photo of baby.
 * Crated as a separate function, to avoid repeating at 1 and 2 screen.
 */

@Composable
fun ImagePickerDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = MediaStore.Images.Media.insertImage(
                context.contentResolver, it, "baby_photo", null
            ).toUri()
            onImageSelected(uri)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("selected_image", ".jpg", context.cacheDir)
            tempFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }

            val cachedImageUri = tempFile.toUri()
            onImageSelected(cachedImageUri)
        }
    }

    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {},
            title = { Text(stringResource(id = R.string.choose_image_source)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        onDismiss()
                        cameraLauncher.launch()
                    }) {
                        Text(stringResource(id = R.string.take_photo))
                    }
                    Button(onClick = {
                        onDismiss()
                        galleryLauncher.launch("image/*")
                    }) {
                        Text(stringResource(id = R.string.pick_from_gallery))
                    }
                }
            }
        )
    }
}