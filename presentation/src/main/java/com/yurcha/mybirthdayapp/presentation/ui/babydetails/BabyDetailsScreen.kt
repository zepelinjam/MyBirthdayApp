package com.yurcha.mybirthdayapp.presentation.ui.babydetails

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.yurcha.mybirthdayapp.presentation.R
import com.yurcha.mybirthdayapp.presentation.ui.utils.PastOrPresentSelectableDates
import com.yurcha.mybirthdayapp.presentation.ui.common.ImagePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BabyDetailsScreen(
    viewModel: BabyDetailsViewModel = hiltViewModel(),
    onNavigateNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    var isShowDatePickerDialog by remember { mutableStateOf(false) }
    val dateState = rememberDatePickerState(selectableDates = PastOrPresentSelectableDates)
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            viewModel.handleEffect(
                effect = effect,
                onNavigateNext = { onNavigateNext() },
                onShowError = { message ->
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    if (isShowDatePickerDialog) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatePickerDialog(
                onDismissRequest = { isShowDatePickerDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            isShowDatePickerDialog = false
                            viewModel.onBirthdayChanged(dateState.selectedDateMillis)
                        }
                    ) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isShowDatePickerDialog = false }
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }
            ) {
                DatePicker(
                    state = dateState,
                    showModeToggle = true
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium
            )

            var nameText by remember { mutableStateOf(TextFieldValue("")) }
            var isFocused by remember { mutableStateOf(false) }

            LaunchedEffect(state.name) {
                val name = state.name
                nameText = TextFieldValue(
                    text = name,
                    selection = TextRange(name.length)
                )
            }

            val maxLength = 35 // name is limited to 35 chars to avoid bugs

            // Name input
            OutlinedTextField(
                value = nameText,
                onValueChange = {
                    if (it.text.length <= maxLength) {
                        nameText = it
                        viewModel.onNameInputChanged(it.text)
                    }
                },
                label = { Text(stringResource(id = R.string.name)) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (isFocused && !it.isFocused) {
                            viewModel.onNameChanged(nameText.text)
                        }
                        isFocused = it.isFocused
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        viewModel.onNameChanged(nameText.text)
                    }
                )
            )

            // Birthday field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isShowDatePickerDialog = true }
            ) {
                OutlinedTextField(
                    value = state.birthday,
                    onValueChange = {},
                    label = { Text(stringResource(id = R.string.birthday)) },
                    enabled = false,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledTextColor = LocalContentColor.current.copy(alpha = 1f),
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            var isImagePickerVisible by remember { mutableStateOf(false) }

            ImagePickerDialog(
                isVisible = isImagePickerVisible,
                onDismiss = { isImagePickerVisible = false },
                onImageSelected = { uri ->
                    viewModel.onPhotoSelected(uri.toString())
                }
            )

            // Picture preview (circular image)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable {
                        isImagePickerVisible = true
                    },
                contentAlignment = Alignment.Center
            ) {

                val painter = if (state.photoUri.isNotBlank()) {
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(Uri.parse(state.photoUri))
                            .crossfade(true)
                            .build()
                    )
                } else {
                    painterResource(R.drawable.ic_picture_default_blue)
                }

                Image(
                    painter = painter,
                    contentDescription = "Baby photo",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            // Submit button
            Button(
                onClick = {
                    viewModel.onSubmitClicked()
                },
                enabled = state.name.isNotBlank() && state.birthday.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.show_birthday_screen))
            }
        }
    }
}