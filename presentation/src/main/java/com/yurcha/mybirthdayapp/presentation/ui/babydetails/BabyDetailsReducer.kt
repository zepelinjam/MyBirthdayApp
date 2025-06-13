package com.yurcha.mybirthdayapp.presentation.ui.babydetails

import androidx.compose.runtime.Immutable
import com.yurcha.mybirthdayapp.presentation.ui.base.Reducer

class BabyDetailsReducer : Reducer<
        BabyDetailsReducer.State,
        BabyDetailsReducer.Event,
        BabyDetailsReducer.Effect> {

    @Immutable
    data class State(
        val name: String,
        val birthday: String,
        val photoUri: String,
        val isShowButtonEnabled: Boolean
    ) : Reducer.ViewState {
        companion object {
            fun initial() = State(
                name = "",
                birthday = "",
                photoUri = "",
                isShowButtonEnabled = false
            )
        }
    }

    @Immutable
    sealed class Event : Reducer.ViewEvent {
        data class OnNameUpdated(val name: String) : Event()
        data class OnBirthdayUpdated(val date: String) : Event()
        data class OnPhotoUpdated(val uri: String) : Event()
        data object ValidateForm : Event()
    }

    @Immutable
    sealed class Effect : Reducer.ViewEffect {
        data object OpenBirthdayScreen : Effect()
        data class ShowError(val message: String) : Effect()
    }

    override fun reduce(
        previousState: State,
        event: Event
    ): Pair<State, Effect?> {
        return when (event) {
            is Event.OnNameUpdated -> {
                val newState = previousState.copy(name = event.name)
                newState.copy(isShowButtonEnabled = validate(newState)) to null
            }

            is Event.OnBirthdayUpdated -> {
                val newState = previousState.copy(birthday = event.date)
                newState.copy(isShowButtonEnabled = validate(newState)) to null
            }

            is Event.OnPhotoUpdated -> {
                previousState.copy(photoUri = event.uri) to null
            }

            is Event.ValidateForm -> {
                if (validate(previousState)) {
                    previousState to Effect.OpenBirthdayScreen
                } else {
                    previousState to Effect.ShowError("Please fill in all fields")
                }
            }
        }
    }

    private fun validate(state: State): Boolean {
        return state.name.isNotBlank() && state.birthday.isNotBlank()
    }
}