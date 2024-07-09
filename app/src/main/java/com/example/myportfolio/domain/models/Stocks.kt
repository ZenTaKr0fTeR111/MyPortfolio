package com.example.myportfolio.domain.models
data class Stocks(val id: Int,
                  val name: String,
                 // val quantity: Int,
                  val price: Double,
                  val currency: String,
                  val Industry: String,
                  val Countryissuer: String,
                  val symbol: String): Asset