package com.example.cats.model

import com.google.gson.annotations.SerializedName

data class Vote (
    @SerializedName("image_id") var imageId : String = "",
    @SerializedName("value") var value : Int = 0
)
