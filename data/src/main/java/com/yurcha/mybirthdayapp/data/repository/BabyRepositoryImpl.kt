package com.yurcha.mybirthdayapp.data.repository

import com.yurcha.mybirthdayapp.data.mapper.toDomain
import com.yurcha.mybirthdayapp.data.mapper.toEntity
import com.yurcha.mybirthdayapp.data.room.AppDatabase
import com.yurcha.mybirthdayapp.domain.model.Baby
import com.yurcha.mybirthdayapp.domain.repository.BabyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BabyRepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : BabyRepository {

    override fun getBaby(): Flow<Baby?> {
        return database.babyDao().getBaby().map { it?.toDomain() }
    }

    override suspend fun saveBaby(baby: Baby) {
        database.babyDao().insertBaby(baby.toEntity())
    }

    override suspend fun updateBaby(baby: Baby) {
        database.babyDao().updateBaby(baby.toEntity())
    }
}