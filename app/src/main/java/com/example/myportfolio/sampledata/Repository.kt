package com.example.myportfolio.sampledata

import com.example.myportfolio.domain.models.Bonds
import com.example.myportfolio.domain.models.Currency
import com.example.myportfolio.domain.models.Stocks


object Repository {
    val assets = listOf(

        Bonds(1,
            "США 10-летние",//название
            42.89 ,//цена
            "USA", //валюта
            "Министерство финансов США",//эмитент
            "15.05.2034",//дата погашения


    ),

       Bonds(
           2,
           "Франция 10-летние",
           3.231,
           "EUR",
           "Центральный банк Франции",
           "25.11.2033",
       ),

Bonds(
  3,
    "Великобритания 10-летние",
    4.1945,
    "GBP",
    "Центральный банк Великобритании",
    "31.01.2033",

),

Stocks(
    4,
    "Apple",
    172.72,
    "USA",
    "Информационные технологии",
    "Соединенные Штаты",
    "AAPL",
    //фруктовый бизнес
),

Stocks(
    5,
    "Alphabet Class C",
    128.64,
    "USA",
    "Телекоммуникации",
    "Соединенные Штаты",
    "GOOG",
),
        Stocks(
            6,
            "Coca-Cola Consolidated Inc",
            636.5,
            "USA",
            "Потребительские товары и услуги",
            "Соединенные Штаты",
            "COKE",


        ),
        Currency(
            7,
            "Доллар США",
            "USD",
            "$",



        ),

Currency(
    8,
    "Белорусский рубль",
    "BYN",
"Br",
),
        Currency(
            9,
            "Фунт стерлингов",
            "GBP",
            "£",
        ),
Currency(
    10,
    "Российский рубль",
    "RUB",
    "₽",

),
        Currency(
            11,
            "Китайская юань",
            "CNY",
            "¥",
        ),
Currency(
    12,
    "Евро",
    "EUR",
    "€",
),
    )
}