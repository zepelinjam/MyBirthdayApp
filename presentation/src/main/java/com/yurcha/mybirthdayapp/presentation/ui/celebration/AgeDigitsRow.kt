package com.yurcha.mybirthdayapp.presentation.ui.celebration

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yurcha.mybirthdayapp.presentation.R

@Composable
fun AgeDigitsRow(modifier: Modifier = Modifier, ageValue: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_left_swirls),
            contentDescription = null,
            modifier = Modifier.padding(end = 22.dp) // padding between number and left icon
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
            modifier = Modifier.padding(start = 22.dp) // padding between number and right icon
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