package com.yurcha.mybirthdayapp.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "baby_table")
data class BabyEntity(
    @PrimaryKey val id: Int = 0, // always = 0, because only one baby
    val name: String,
    val birthday: Long?,
    val photoUri: String
)