package com.yurcha.mybirthdayapp.domain.usecase

import com.yurcha.mybirthdayapp.domain.model.Baby
import com.yurcha.mybirthdayapp.domain.repository.BabyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBabyUseCase @Inject constructor(private val repository: BabyRepository)  {
    operator fun invoke(): Flow<Baby?> = repository.getBaby()
}