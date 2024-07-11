package com.example.myportfolio.domain.models

data class Stocks(
    val id: Int,
    val name: String,
    val price: Double,
    val currency: String,
    val industry: String,
    val countryIssuer: String,
    val symbol: String
) : Asset