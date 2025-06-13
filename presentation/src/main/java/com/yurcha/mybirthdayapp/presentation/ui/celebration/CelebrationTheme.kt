package com.yurcha.mybirthdayapp.presentation.ui.celebration

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.yurcha.mybirthdayapp.presentation.R
import com.yurcha.mybirthdayapp.presentation.ui.theme.Blue_bg
import com.yurcha.mybirthdayapp.presentation.ui.theme.Blue_border
import com.yurcha.mybirthdayapp.presentation.ui.theme.Green_bg
import com.yurcha.mybirthdayapp.presentation.ui.theme.Green_border
import com.yurcha.mybirthdayapp.presentation.ui.theme.Yellow_bg
import com.yurcha.mybirthdayapp.presentation.ui.theme.Yellow_border

enum class CelebrationTheme(
    val backgroundColor: Color,
    val borderColor: Color,
    @DrawableRes val iconImageTemplate: Int,
    @DrawableRes val iconCamera: Int,
    @DrawableRes val overlayImage: Int
) {
    YELLOW(
        backgroundColor = Yellow_bg,
        borderColor = Yellow_border,
        iconImageTemplate = R.drawable.ic_picture_default_yellow,
        iconCamera = R.drawable.ic_add_picture_yellow,
        overlayImage = R.drawable.yellow_elephant
    ),
    BLUE(
        backgroundColor = Blue_bg,
        borderColor = Blue_border,
        iconImageTemplate = R.drawable.ic_picture_default_blue,
        iconCamera = R.drawable.ic_add_picture_blue,
        overlayImage = R.drawable.blue_pelican
    ),
    GREEN(
        backgroundColor = Green_bg,
        borderColor = Green_border,
        iconImageTemplate = R.drawable.ic_picture_default_green,
        iconCamera = R.drawable.ic_add_picture_green,
        overlayImage = R.drawable.green_fox
    )
}
