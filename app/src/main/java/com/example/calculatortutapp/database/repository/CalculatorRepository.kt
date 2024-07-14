package com.example.calculatortutapp.database.repository

import com.example.calculatortutapp.database.dao.CalculationDao
import com.example.calculatortutapp.database.entities.Calculation
import kotlinx.coroutines.flow.Flow

class CalculatorRepository(private val calculationDao: CalculationDao) {
    val allCalculations: Flow<List<Calculation>> = calculationDao.getAllCalculations()

    suspend fun insert(calculation: Calculation) {
        calculationDao.insert(calculation)
    }

    suspend fun deleteAll() {
        calculationDao.deleteAll()
    }
}
