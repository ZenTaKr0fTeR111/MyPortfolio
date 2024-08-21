package com.example.myportfolio.data.db.conversion_rates

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.myportfolio.domain.models.CurrencyCode

@Dao
interface ConversionRateDao {
    @Query(
        """ 
            SELECT * FROM conversion_rates 
            WHERE currency = :currencyCode 
            ORDER BY date DESC
            LIMIT :days 
        """
    )
    suspend fun getRatesForCurrency(
        currencyCode: CurrencyCode,
        days: Int
    ): List<ConversionRateEntity>

    @Query(
        """
            SELECT * FROM conversion_rates
            GROUP BY date
            ORDER BY date DESC
            LIMIT :days
        """
    )
    suspend fun getMostRecentEntries(days: Int): List<ConversionRateEntity>

    @Upsert
    suspend fun insertRates(rates: List<ConversionRateEntity>)
}
