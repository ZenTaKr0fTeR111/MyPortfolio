package com.example.myportfolio.domain.models

data class Bonds(
    val id: Int,
    val name: String,
    val price: Double,
    val currency: String,
    val issuer: String,
    val maturityDate: String
) : Asset