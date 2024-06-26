package com.tyro.taptopay.sdk.demo

import com.tyro.taptopay.sdk.api.ConnectionProvider
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class SdkDemoConnectionProvider(engine: HttpClientEngine = CIO.create()) : ConnectionProvider {
    lateinit var readerId: String

    private val client =
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    },
                )
            }
        }

    // update this to fetch your connection secret
    override suspend fun createConnection(): String {
        return client.post(UPDATE__THIS__CONNECTION_SECRET_ENDPOINT_URL) {
            contentType(ContentType.Application.Json)
            setBody(DemoConnectionRequestBody(readerId))
        }.body<DemoConnectionResponse>().connectionSecret
    }

    @Serializable
    data class DemoConnectionRequestBody(val readerId: String)

    @Serializable
    data class DemoConnectionResponse(val connectionSecret: String)

    companion object {
        // TODO
        // create an endpoint on your server to generate the connection secret
        const val UPDATE__THIS__CONNECTION_SECRET_ENDPOINT_URL = "https://api.tyro.com/connect/tap-to-pay/demo/connections"

    }
}