package com.moineaufactory.lingvasferoapi.api.spotify

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApi {

    @GET("shows/{show_id}/episodes")
    fun getEpisodes(
        @Path(value = "show_id", encoded = true) showId: String,
        //@Query("market") key: String = "FR",
    ): Call<SpotifyApiResponse>

    @GET("search")
    fun getPodcastsPreview(
        @Query("q") search: String,
        @Query("type") type: String = "show",
        @Query("limit") limit: Int = 10,
        @Query("market") market: String = "FR"
    ): Call<SpotifySearchShowResponseDto>

    @GET("shows/{show_id}")
    fun getPodcastDetails(
        @Path(value = "show_id", encoded = true) showId: String,
        //@Query("market") key: String = "FR",
    ): Call<SpotifyItem>

}