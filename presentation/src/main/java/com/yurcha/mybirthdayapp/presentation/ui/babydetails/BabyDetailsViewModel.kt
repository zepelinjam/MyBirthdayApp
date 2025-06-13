package com.yurcha.mybirthdayapp.presentation.ui.babydetails

import androidx.lifecycle.viewModelScope
import com.yurcha.mybirthdayapp.domain.utils.toDateFormat
import com.yurcha.mybirthdayapp.presentation.ui.base.BaseViewModel
import com.yurcha.mybirthdayapp.domain.model.Baby
import com.yurcha.mybirthdayapp.domain.usecase.GetBabyUseCase
import com.yurcha.mybirthdayapp.domain.usecase.SaveBabyUseCase
import com.yurcha.mybirthdayapp.domain.usecase.UpdateBabyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BabyDetailsViewModel @Inject constructor(
    private val getBabyUseCase: GetBabyUseCase,
    private val saveBabyUseCase: SaveBabyUseCase,
    private val updateBabyUseCase: UpdateBabyUseCase,
) : BaseViewModel<
        BabyDetailsReducer.State,
        BabyDetailsReducer.Event,
        BabyDetailsReducer.Effect
        >(
    initialState = BabyDetailsReducer.State.initial(),
    reducer = BabyDetailsReducer()
) {
    private var currentBaby: Baby? = null

    init {
        getInitialData()
    }

    private fun getInitialData() {
        viewModelScope.launch {
            getBabyUseCase().collect { result ->
                result?.let {
                    currentBaby = it
                    sendEvent(BabyDetailsReducer.Event.OnNameUpdated(it.name))
                    sendEvent(BabyDetailsReducer.Event.OnBirthdayUpdated(it.birthday.toDateFormat()))
                    sendEvent(BabyDetailsReducer.Event.OnPhotoUpdated(it.photoUri))
                } ?: kotlin.run {
                    val emptyBaby = Baby.empty()
                    currentBaby = emptyBaby
                    saveBabyUseCase.invoke(emptyBaby)
                }
            }
        }
    }

    private fun updateBabyData(name: String? = null, birthday: Long? = null, photoUri: String? = null) {
        viewModelScope.launch {
            currentBaby?.let { baby ->
                val updatedBaby = baby.copy(
                    name = name ?: baby.name,
                    birthday = birthday ?: baby.birthday,
                    photoUri = photoUri ?: baby.photoUri
                )
                currentBaby = updatedBaby
                updateBabyUseCase.invoke(updatedBaby)

                name?.let { sendEvent(BabyDetailsReducer.Event.OnNameUpdated(it)) }
                birthday?.let { sendEvent(BabyDetailsReducer.Event.OnBirthdayUpdated(it.toDateFormat())) }
                photoUri?.let { sendEvent(BabyDetailsReducer.Event.OnPhotoUpdated(it)) }
            }
        }
    }

    fun onNameChanged(name: String) {
        updateBabyData(name = name)
    }

    fun onBirthdayChanged(birthday: Long?) {
        updateBabyData(birthday = birthday)
    }

    fun onPhotoSelected(uri: String) {
        updateBabyData(photoUri = uri)
    }

    fun onSubmitClicked() {
        sendEffect(BabyDetailsReducer.Effect.OpenCelebrationScreen)
    }

    fun handleEffect(
        effect: BabyDetailsReducer.Effect,
        onNavigateNext: () -> Unit,
        onShowError: (String) -> Unit
    ) {
        when (effect) {
            is BabyDetailsReducer.Effect.OpenCelebrationScreen -> onNavigateNext()
            is BabyDetailsReducer.Effect.ShowError -> onShowError(effect.message)
        }
    }

}