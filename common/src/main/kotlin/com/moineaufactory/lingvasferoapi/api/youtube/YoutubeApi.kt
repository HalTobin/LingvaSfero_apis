package com.moineaufactory.lingvasferoapi.api.youtube

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

    @GET("search")
    fun getChannelsPreview(
        @Query("key") key: String,
        @Query("q") search: String,
        @Query("type") type: String = "channel",
        @Query("part") part: String = "snippet"
    ): Call<YoutubeApiResponse>

    @GET("channels")
    fun getChannelDetails(
        @Query("key") key: String,
        @Query("id") channelId: String,
        @Query("part") part: String = "snippet"
    ): Call<YoutubeApiResponse>

}