package com.kubaty.catfacts.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kubaty.catfacts.model.CatFact
import com.kubaty.catfacts.util.CatFactUtil.DEFAULT_JSON
import com.kubaty.catfacts.util.JsonUtil
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeFactsService @Inject constructor(private val jsonUtil: JsonUtil) : FactsService {

    var jsonFileName: String = DEFAULT_JSON

    override suspend fun getFacts(animalType: String, amount: Int): Response<List<CatFact>> {
        val json = jsonUtil.readJSONFromAsset(jsonFileName)
        val facts =
            Gson().fromJson<List<CatFact>>(json, object : TypeToken<List<CatFact>>() {}.type)
        return Response.success(facts)
    }
}