package com.moineaufactory.lingvasferoapi.feature.content.data.api.youtube

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {

    @GET("playlistItems")
    fun getVideos(
        @Query("key") key: String,
        @Query("part") part: String = "snippet",
        @Query("maxResults") maxResults: Int = 50,
        @Query("playlistId") playlistId: String
    ): Call<YoutubeApiResponse>

}