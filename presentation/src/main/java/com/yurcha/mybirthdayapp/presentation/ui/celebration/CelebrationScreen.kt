package com.yurcha.mybirthdayapp.presentation.ui.celebration

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.yurcha.mybirthdayapp.presentation.R
import com.yurcha.mybirthdayapp.presentation.ui.common.ImagePickerDialog
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeApi::class)
@Composable
fun CelebrationScreen(
    viewModel: CelebrationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val theme = remember { CelebrationTheme.entries.random() }
    val scope = rememberCoroutineScope()
    val captureController = rememberCaptureController()
    var isSharing by remember { mutableStateOf(false) }
    var isImagePickerVisible by remember { mutableStateOf(false) }

    val customPaddingBelow = 110.dp // padding from the bottom side to the Nanit logo

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            viewModel.handleEffect(
                effect,
                onNavigateNext = onNavigateBack,
                onShowError = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    LaunchedEffect(isSharing) {
        if (isSharing) {
            kotlinx.coroutines.delay(100)
            scope.launch {
                val bitmap = captureController.captureAsync().await()
                shareBitmap(bitmap, context)
                kotlinx.coroutines.delay(300)
                isSharing = false
            }
        }
    }

    ImagePickerDialog(
        isVisible = isImagePickerVisible,
        onDismiss = { isImagePickerVisible = false },
        onImageSelected = { uri ->
            viewModel.onPhotoSelected(uri.toString())
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .capturable(captureController)
            .background(theme.backgroundColor)
    ) {
        // Background image
        Image(
            painter = painterResource(theme.overlayImage),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter) // pinned to the bottom side of the screen as required
                .zIndex(2f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 50.dp)
                .padding(top = 20.dp)
                .zIndex(1f)
        ) {
            Spacer(modifier = Modifier.weight(1f)) // helps center between top and baby image

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                AgeContentSection(
                    name = state.name,
                    ageValue = state.ageValue,
                    isAgeInYears = state.isAgeInYears
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // helps center between top and baby image

            // Baby image
            PhotoSection(
                photoUri = state.photoUri,
                theme = theme,
                onCameraClick = { isImagePickerVisible = true },
                isSharing = isSharing,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = customPaddingBelow + 15.dp) // padding as required in Figma
            )
        }

        // Nanit logo
        Image(
            painter = painterResource(R.drawable.ic_nanit_logo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = customPaddingBelow) // custom padding from the bottom side of the screen
                .size(width = 59.dp, height = 20.dp)
                .zIndex(3f)
        )

        // Control buttons (at the top of the screen)
        if (!isSharing) {
            ControlButtonsSection(
                onBack = onNavigateBack,
                onShare = { isSharing = true }
            )
        }
    }
}

private fun shareBitmap(imageBitmap: ImageBitmap, context: Context) {
    val androidBitmap = imageBitmap.asAndroidBitmap()

    val cachePath = File(context.cacheDir, "images")
    cachePath.mkdirs()

    val file = File(cachePath, "screenshot.png")
    FileOutputStream(file).use { outputStream ->
        androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(intent, "Share via"))
}