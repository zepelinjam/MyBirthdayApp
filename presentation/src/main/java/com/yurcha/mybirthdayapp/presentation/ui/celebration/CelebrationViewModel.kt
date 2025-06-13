package com.yurcha.mybirthdayapp.presentation.ui.celebration

import androidx.lifecycle.viewModelScope
import com.yurcha.mybirthdayapp.domain.model.Baby
import com.yurcha.mybirthdayapp.domain.usecase.GetBabyUseCase
import com.yurcha.mybirthdayapp.domain.usecase.UpdateBabyUseCase
import com.yurcha.mybirthdayapp.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@HiltViewModel
class CelebrationViewModel @Inject constructor(
    private val getBabyUseCase: GetBabyUseCase,
    private val updateBabyUseCase: UpdateBabyUseCase
) : BaseViewModel<
        CelebrationReducer.State,
        CelebrationReducer.Event,
        CelebrationReducer.Effect
        >(
    initialState = CelebrationReducer.State.initial(),
    reducer = CelebrationReducer()
) {
    private var currentBaby: Baby? = null

    init {
        getInitialData()
    }

    // fetch info from DB and calculate age in month / years
    private fun getInitialData() {
        viewModelScope.launch {
            getBabyUseCase().collect { baby ->
                baby?.let {
                    currentBaby = it
                    val name = it.name
                    val photoUri = it.photoUri
                    val birthday = it.birthday ?: 0L
                    val birthDate = Instant.ofEpochMilli(birthday).atZone(ZoneId.systemDefault()).toLocalDate()
                    val now = LocalDate.now()
                    val period = Period.between(birthDate, now)
                    val ageValue = period.years.takeIf { it > 0 } ?: period.months
                    val isYears = period.years > 0

                    sendEvent(
                        CelebrationReducer.Event.OnBabyLoaded(
                            name = name,
                            ageValue = ageValue,
                            isAgeInYears = isYears,
                            photoUri = photoUri
                        )
                    )
                }
            }
        }
    }

    fun onPhotoSelected(uri: String) {
        viewModelScope.launch {
            currentBaby?.let { baby ->
                val updated = baby.copy(photoUri = uri)
                currentBaby = updated
                updateBabyUseCase(updated)
                sendEvent(CelebrationReducer.Event.OnPhotoUpdated(uri))
            }
        }
    }

    fun onSubmitClicked() {
        sendEvent(CelebrationReducer.Event.OnShowCelebration)
    }

    fun handleEffect(
        effect: CelebrationReducer.Effect,
        onNavigateNext: () -> Unit,
        onShowError: (String) -> Unit
    ) {
        when (effect) {
            is CelebrationReducer.Effect.BackToDetailsScreen -> onNavigateNext()
            is CelebrationReducer.Effect.ShowError -> onShowError(effect.message)
        }
    }
}