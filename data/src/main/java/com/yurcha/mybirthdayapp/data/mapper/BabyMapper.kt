package com.yurcha.mybirthdayapp.data.mapper

import com.yurcha.mybirthdayapp.data.room.entity.BabyEntity
import com.yurcha.mybirthdayapp.domain.model.Baby

fun BabyEntity.toDomain(): Baby = Baby(
    name = name,
    birthday = birthday,
    photoUri = photoUri
)

fun Baby.toEntity(): BabyEntity = BabyEntity(
    id = 0,
    name = name,
    birthday = birthday,
    photoUri = photoUri
)