package com.yurcha.mybirthdayapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yurcha.mybirthdayapp.data.room.dao.BabyDao
import com.yurcha.mybirthdayapp.data.room.entity.BabyEntity

@Database(entities = [BabyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun babyDao(): BabyDao
}