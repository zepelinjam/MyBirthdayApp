package com.yurcha.mybirthdayapp.presentation.ui.celebration

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun PhotoSection(
    photoUri: String,
    theme: CelebrationTheme,
    onCameraClick: () -> Unit,
    isSharing: Boolean
) {
    val imageSize = 200.dp
    val borderWidth = 7.dp
    val cameraSize = 36.dp
    val radius = imageSize / 2 + borderWidth / 2
    val diagonalOffset = (radius * 0.7071f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(imageSize + cameraSize / 2),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(imageSize)
                    .clip(CircleShape)
                    .border(borderWidth, theme.borderColor, CircleShape)
            ) {
                val painter = if (photoUri.isNotBlank()) {
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(photoUri)
                            .build()
                    )
                } else {
                    painterResource(theme.iconImageTemplate)
                }

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (!isSharing) {
                Image(
                    painter = painterResource(theme.iconCamera),
                    contentDescription = null,
                    modifier = Modifier
                        .size(cameraSize)
                        .offset(x = diagonalOffset, y = -diagonalOffset)
                        .zIndex(3f)
                        .clickable{ onCameraClick() }
                )
            }
        }
    }
}