package com.yurcha.mybirthdayapp.presentation.ui.celebration

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yurcha.mybirthdayapp.presentation.R
import com.yurcha.mybirthdayapp.presentation.ui.utils.rememberFlowWithLifecycle
@Composable
fun CelebrationScreen(
    viewModel: CelebrationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val effect = rememberFlowWithLifecycle(viewModel.effect)
    val theme = remember { CelebrationTheme.entries.random() }

    Box(modifier = Modifier.fillMaxSize().background(theme.backgroundColor)) {

        // Background overlay specific to theme
        Image(
            painter = painterResource(theme.overlayImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().align(Alignment.BottomCenter),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.today_birthday, state.name).uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Left icon + age digits + right icon
            AgeDigitsRow(ageValue = state.ageValue)

            // 4. Month/Year text
            Text(
                text = if (state.isAgeInYears) {
                    stringResource(id = R.string.years_old).uppercase()
                } else {
                    stringResource(id = R.string.month_old).uppercase()
                       },
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // 5. Baby photo in circle with border
            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(4.dp, theme.borderColor, CircleShape)
                ) {
                    if (state.photoUri != null) {
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

                // 6. Camera icon button
                Image(
                    painter = painterResource(theme.iconCamera),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 24.dp, y = 24.dp)
                        .clickable { /*onChangePhoto()*/ }
                )
            }

            // 7. Logo
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_nanit_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp)
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
        Image(painterResource(id = R.drawable.ic_left_swirls), contentDescription = null)

        Row {
            ageValue.toString().forEach { digit ->
                val resId = getDigitResId(digit)
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = digit.toString(),
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Image(painterResource(id = R.drawable.ic_right_swirls), contentDescription = null)
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