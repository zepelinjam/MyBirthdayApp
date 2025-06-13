package com.yurcha.mybirthdayapp.domain.repository

import com.yurcha.mybirthdayapp.domain.model.Baby
import kotlinx.coroutines.flow.Flow

interface BabyRepository {
    fun getBaby(): Flow<Baby?>
    suspend fun saveBaby(baby: Baby)
    suspend fun updateBaby(baby: Baby)
}
