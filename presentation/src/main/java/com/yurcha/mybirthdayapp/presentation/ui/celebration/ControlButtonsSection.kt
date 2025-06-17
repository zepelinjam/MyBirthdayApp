package com.yurcha.mybirthdayapp.presentation.ui.celebration

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yurcha.mybirthdayapp.presentation.R

@Composable
fun ControlButtonsSection(onBack: () -> Unit, onShare: () -> Unit) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back_temporary),
            contentDescription = "Back",
            modifier = Modifier
                .size(30.dp)
                .clickable { onBack() }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_share_temporary),
            contentDescription = "Share",
            modifier = Modifier
                .size(30.dp)
                .clickable { onShare() }
        )
    }
}