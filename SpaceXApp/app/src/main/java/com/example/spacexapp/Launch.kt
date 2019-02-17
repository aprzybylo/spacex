package com.example.spacexapp

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


data class Launch(
    var launch_success: Boolean?,
    var launch_date_utc: Date?,
    var links: Links?,
    var mission_name: String?,
    var rocket: Rocket?
) {
    class Deserializer : ResponseDeserializable<ArrayList<Launch>> {
        val turnsType = object : TypeToken<List<Launch>>() {}.type
        override fun deserialize(content: String): ArrayList<Launch>? = Gson().fromJson(content, turnsType)
    }
}

data class Links(
    var wikipedia: String?,
    var mission_patch: String?
)

data class Rocket(
   var rocket_name: String?
)
