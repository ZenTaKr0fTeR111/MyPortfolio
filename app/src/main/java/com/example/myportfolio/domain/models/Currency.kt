package com.example.myportfolio.domain.models

data class Currency(
    val id: Int,
    val name: String,
    val secondName: String,
    val symbol: String,
) : Asset
