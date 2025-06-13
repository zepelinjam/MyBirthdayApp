package com.yurcha.mybirthdayapp.presentation.ui.celebration

import androidx.compose.runtime.Immutable
import com.yurcha.mybirthdayapp.presentation.ui.base.Reducer

class CelebrationReducer : Reducer<
        CelebrationReducer.State,
        CelebrationReducer.Event,
        CelebrationReducer.Effect> {

    @Immutable
    data class State(
        val name: String,
        val ageValue: Int,
        val isAgeInYears: Boolean,
        val photoUri: String
    ) : Reducer.ViewState {
        companion object {
            fun initial() = State(
                name = "",
                ageValue = 0,
                isAgeInYears = false,
                photoUri = ""
            )
        }
    }

    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data class OnBabyLoaded(val name: String, val ageValue: Int, val isAgeInYears: Boolean, val photoUri: String) : Event()
        data class OnPhotoUpdated(val uri: String) : Event()
        data object OnShowCelebration : Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object BackToDetailsScreen : Effect()
        data class ShowError(val message: String) : Effect()
    }

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.OnBabyLoaded -> {
                val updatedState = previousState.copy(
                    name = event.name,
                    ageValue = event.ageValue,
                    isAgeInYears = event.isAgeInYears,
                    photoUri = event.photoUri
                )
                updatedState to null
            }

            is Event.OnPhotoUpdated -> {
                previousState.copy(photoUri = event.uri) to null
            }

            is Event.OnShowCelebration -> {
                previousState to Effect.BackToDetailsScreen
            }
        }
    }
}