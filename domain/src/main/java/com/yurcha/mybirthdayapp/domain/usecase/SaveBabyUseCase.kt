package com.yurcha.mybirthdayapp.domain.usecase

import com.yurcha.mybirthdayapp.domain.model.Baby
import com.yurcha.mybirthdayapp.domain.repository.BabyRepository
import javax.inject.Inject

class SaveBabyUseCase @Inject constructor(private val repository: BabyRepository)  {
    suspend operator fun invoke(baby: Baby) {
        repository.saveBaby(baby)
    }
}