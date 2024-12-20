package com.foryou.androiddoctruyen.di

import com.foryou.androiddoctruyen.datasource.remote.ApiURL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val KtorModule = module {

    single { provideHttpClient() }

}

fun provideHttpClient(): HttpClient {
    return HttpClient {
        // Enable JSON support
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = ApiURL.DOMAIN_API
            }
        }
    }
}