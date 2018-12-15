package com.odora.id.footballapps.model

import com.google.gson.annotations.SerializedName

data class Players(

        @SerializedName("idPlayer")
        var idPlayer: String? = null,

        @SerializedName("strPlayer")
        var strPlayer: String? = null,

        @SerializedName("dateBorn")
        var dateBorn: String? = null,

        @SerializedName("strDescriptionEN")
        var strDescriptionEN: String? = null,

        @SerializedName("strPosition")
        var strPosition: String? = null,

        @SerializedName("strFanart1")
        var strFanart1: String? = null,

        @SerializedName("strThumb")
        var strThumb: String? = null

)