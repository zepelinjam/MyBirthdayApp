package com.yurcha.mybirthdayapp.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yurcha.mybirthdayapp.data.room.entity.BabyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BabyDao {

    @Query("SELECT * FROM baby_table WHERE id = 0 LIMIT 1")
    fun getBaby(): Flow<BabyEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBaby(baby: BabyEntity)

    @Update
    suspend fun updateBaby(baby: BabyEntity)

}