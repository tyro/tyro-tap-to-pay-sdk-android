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

class SdkDemoConnectionProvider(
  private val connectionUrl: String,
  engine: HttpClientEngine = CIO.create(),
) : ConnectionProvider {
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

  override suspend fun createConnection(): String = client.post(connectionUrl) {
    contentType(ContentType.Application.Json)
    setBody(DemoConnectionRequestBody(readerId))
  }.body<DemoConnectionResponse>().connectionSecret

  @Serializable
  data class DemoConnectionRequestBody(val readerId: String)

  @Serializable
  data class DemoConnectionResponse(val connectionSecret: String)
}
