package com.moineaufactory.lingvasferoapi.feature.content.data.api.spotify

import org.springframework.beans.factory.annotation.Value
import java.io.IOException
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
class SpotifyAuthInterceptor(
    private val configuration: SpotifyConfiguration
): Interceptor {

    private val authUrl = "https://accounts.spotify.com/api/token"

    val clientId: String = configuration.id
    val clientSecret: String = configuration.secret

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val authHeader = "Basic " + "$clientId:$clientSecret".encodeToByteArray().toString(Charsets.ISO_8859_1).base64()
        val request = chain.request().newBuilder()
            .header("Authorization", authHeader)
            .post(
                FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .build())
            .url(authUrl)
            .build()
        return chain.proceed(request)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun String.base64(): String {
        return Base64.encode(this.toByteArray())
    }
}

@ConfigurationProperties(prefix = "spotify.client")
@Component
data class SpotifyConfiguration(
    var id: String = "",
    var secret: String = ""
)