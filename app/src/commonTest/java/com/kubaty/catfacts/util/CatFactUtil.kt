package com.kubaty.catfacts.util

import com.kubaty.catfacts.R
import com.kubaty.catfacts.model.CatFact
import java.util.*

object CatFactUtil {
    const val DEFAULT_JSON = "facts.json"
    const val TEST_ANIMAL_TYPE = "cat"
    const val TEST_ANIMAL_AMOUNT = 30
    private const val FACT_TEXT = "Some fact about cat."
    val DUMMY_CAT_FACT =
        CatFact("591f98783b90f7150a19c1bd", Date(), FACT_TEXT, R.drawable.ic_launcher_foreground)

}