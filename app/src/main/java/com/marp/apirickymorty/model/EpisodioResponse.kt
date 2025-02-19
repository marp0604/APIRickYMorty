package com.marp.rickymortyapi.model

import com.google.gson.annotations.SerializedName

data class EpisodioResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("episode") var episode: String,
    @SerializedName("characters") var characters: List<String>

)
