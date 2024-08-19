package com.example.myportfolio.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConversionRateApiService {
    /**
     * [startDate] & [endDate] are string dates of format "yyyy-MM-dd"
     */
    @GET("exrates/rates/dynamics/{curId}")
    suspend fun getRateDynamicByCurrencyId(
        @Path("curId") currencyId: Long,
        @Query("startdate") startDate: String,
        @Query("enddate") endDate: String
    ): List<SerializableConversionRate>

    @GET("exrates/rates/periodicity=0")
    suspend fun getDailyRates(): List<SerializableConversionRate>
}
