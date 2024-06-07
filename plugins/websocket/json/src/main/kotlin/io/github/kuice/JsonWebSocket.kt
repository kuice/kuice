package io.github.kuice

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provider
import io.ktor.serialization.WebsocketContentConverter
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import kotlinx.serialization.json.Json

class JsonWebSocketContentConverterProvider @Inject constructor(
    private val format: Json,
) : Provider<WebsocketContentConverter> {
    override fun get(): WebsocketContentConverter = KotlinxWebsocketSerializationConverter(format)
}

object WebSocketModule : AbstractModule() {
    override fun configure() {
        bind(WebsocketContentConverter::class.java)
            .toProvider(JsonWebSocketContentConverterProvider::class.java).asEagerSingleton()
    }
}
