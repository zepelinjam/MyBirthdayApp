package com.yurcha.mybirthdayapp.presentation.ui.celebration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yurcha.mybirthdayapp.presentation.R
import com.yurcha.mybirthdayapp.presentation.ui.theme.Dark_blue

@Composable
fun AgeContentSection(
    name: String,
    ageValue: Int,
    isAgeInYears: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.today_birthday, name).uppercase(),
            fontSize = 21.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = Dark_blue
        )

        Spacer(modifier = Modifier.height(13.dp)) // margin 13 as mentioned in Figma

        AgeDigitsRow(ageValue = ageValue)

        Spacer(modifier = Modifier.height(14.dp)) // margin 14 as mentioned in Figma

        val ageTextRes = if (isAgeInYears) {
            if (ageValue == 1) R.string.year_old else R.string.years_old
        } else {
            if (ageValue == 1) R.string.month_old else R.string.months_old
        }

        Text(
            text = stringResource(id = ageTextRes).uppercase(),
            color = Dark_blue,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}