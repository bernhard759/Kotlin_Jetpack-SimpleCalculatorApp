package com.example.calculatortutapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.calculatortutapp.database.entities.Calculation
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationDao {
    @Query("SELECT * FROM calculation_table")
    fun getAllCalculations(): Flow<List<Calculation>>

    @Insert
    suspend fun insert(calculation: Calculation)

    @Query("DELETE FROM calculation_table")
    suspend fun deleteAll()
}
