package com.kubaty.catfacts.model

import java.util.*

data class CatFact(
    val _id: String,
    val updatedAt: Date,
    val text: String
)