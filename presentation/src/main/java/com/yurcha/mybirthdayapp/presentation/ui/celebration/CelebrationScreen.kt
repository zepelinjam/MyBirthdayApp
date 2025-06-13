package com.yurcha.mybirthdayapp.presentation.ui.celebration

import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yurcha.mybirthdayapp.presentation.R
import com.yurcha.mybirthdayapp.presentation.ui.common.ImagePickerDialog
import com.yurcha.mybirthdayapp.presentation.ui.theme.Dark_blue
import java.io.File

@Composable
fun CelebrationScreen(
    viewModel: CelebrationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val theme = remember { CelebrationTheme.entries.random() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            viewModel.handleEffect(
                effect = effect,
                onNavigateNext = { onNavigateBack() },
                onShowError = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    var isImagePickerVisible by remember { mutableStateOf(false) }

    ImagePickerDialog (
        isVisible = isImagePickerVisible,
        onDismiss = { isImagePickerVisible = false },
        onImageSelected = { uri ->
            viewModel.onPhotoSelected(uri.toString())
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.backgroundColor)
    ) {
        // back button
        Image(
            painter = painterResource(id = R.drawable.ic_back_temporary),
            contentDescription = "Back",
            modifier = Modifier
                .padding(16.dp)
                .size(30.dp)
                .align(Alignment.TopStart)
                .clickable { onNavigateBack() }
        )

        // overlay picture
        Image(
            painter = painterResource(theme.overlayImage),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(1f),
            contentScale = ContentScale.FillWidth
        )

        // main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // baby name
            Text(
                text = stringResource(id = R.string.today_birthday, state.name).uppercase(),
                fontSize = 21.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 21.sp,
                letterSpacing = (-0.42).sp,
                color = Dark_blue,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .width(252.dp)
                    .height(58.dp)
            )

            Spacer(modifier = Modifier.height(13.dp))

            // Age digits
            AgeDigitsRow(ageValue = state.ageValue)

            Spacer(modifier = Modifier.height(14.dp))

            // month / years sign
            Text(
                text = if (state.isAgeInYears) {
                    if (state.ageValue > 1)
                        stringResource(id = R.string.years_old).uppercase()
                    else
                        stringResource(id = R.string.year_old).uppercase()
                } else {
                    if (state.ageValue > 1)
                        stringResource(id = R.string.months_old).uppercase()
                    else
                        stringResource(id = R.string.month_old).uppercase()
                },
                style = MaterialTheme.typography.headlineMedium,
                color = Dark_blue,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            // picture
            val imageSize = 200.dp
            val borderWidth = 7.dp
            val cameraSize = 48.dp
            val radius = imageSize / 2 + borderWidth / 2
            val diagonalOffset = (radius * 0.7071f)

            Box(
                modifier = Modifier
                    .size(imageSize + cameraSize / 2),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(imageSize)
                        .clip(CircleShape)
                        .border(borderWidth, theme.borderColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.photoUri.isNotBlank()) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(state.photoUri)
                                    .build()
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(theme.iconImageTemplate),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // camera button
                Image(
                    painter = painterResource(theme.iconCamera),
                    contentDescription = null,
                    modifier = Modifier
                        .size(cameraSize)
                        .offset(x = diagonalOffset, y = -diagonalOffset)
                        .zIndex(3f)
                        .clickable { isImagePickerVisible = true }
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Nanit logo
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_nanit_logo),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
                    .width(59.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun AgeDigitsRow(modifier: Modifier = Modifier, ageValue: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_left_swirls),
            contentDescription = null,
            modifier = Modifier.padding(end = 22.dp)
        )

        Row {
            ageValue.toString().forEach { digit ->
                val resId = getDigitResId(digit)
                Box(
                    modifier = Modifier
                        .width(51.dp)
                        .height(88.dp)
                ) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = digit.toString(),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_right_swirls),
            contentDescription = null,
            modifier = Modifier.padding(start = 22.dp)
        )
    }
}

private fun getDigitResId(digit: Char): Int {
    return when (digit) {
        '0' -> R.drawable.ic_0
        '1' -> R.drawable.ic_1
        '2' -> R.drawable.ic_2
        '3' -> R.drawable.ic_3
        '4' -> R.drawable.ic_4
        '5' -> R.drawable.ic_5
        '6' -> R.drawable.ic_6
        '7' -> R.drawable.ic_7
        '8' -> R.drawable.ic_8
        '9' -> R.drawable.ic_9
        else -> R.drawable.ic_0
    }
}