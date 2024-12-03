package com.moineaufactory.lingvasferoapi.api.spotify

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApi {

    @GET("shows/{show_id}/episodes")
    fun getPodcasts(
        @Path(value = "show_id", encoded = true) showId: String,
        //@Query("market") key: String = "FR",
    ): Call<SpotifyApiResponse>

}