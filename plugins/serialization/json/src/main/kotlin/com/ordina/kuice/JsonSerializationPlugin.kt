@file:OptIn(ExperimentalSerializationApi::class)

package com.ordina.kuice

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.ProvidedBy
import com.google.inject.Provider
import com.ordina.kuice.config.ConfigLoader
import com.ordina.kuice.config.ConfigProvider
import com.ordina.kuice.ktor.plugins.BaseRouteScopedPlugin
import com.typesafe.config.Config
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.PluginInstance
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.contentnegotiation.ContentNegotiationConfig
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

class JsonSerializationPlugin @Inject constructor(format: Json) :
    BaseRouteScopedPlugin<ContentNegotiationConfig, PluginInstance>(ContentNegotiation, { json(format) })

@ProvidedBy(JsonSerializationConfigProvider::class)
data class JsonSerializationConfig(
    val encodeDefaults: Boolean = false,
    val ignoreUnknownKeys: Boolean = false,
    val isLenient: Boolean = false,
    val allowStructuredMapKeys: Boolean = false,
    val prettyPrint: Boolean = false,
    val explicitNulls: Boolean = true,
    val prettyPrintIndent: String = "    ",
    val coerceInputValues: Boolean = false,
    val useArrayPolymorphism: Boolean = false,
    val classDiscriminator: String = "type",
    val allowSpecialFloatingPointValues: Boolean = false,
    val useAlternativeNames: Boolean = true,
    val namingStrategy: JsonNamingStrategy? = null,
)

class JsonSerializationConfigProvider @Inject constructor(config: Config) :
    ConfigProvider<JsonSerializationConfig>(JsonSerializationConfigurationLoader, config)

object JsonSerializationConfigurationLoader : ConfigLoader<JsonSerializationConfig>("ktor.json", {
    JsonSerializationConfig(
        encodeDefaults = getBoolean("encodeDefaults"),
        ignoreUnknownKeys = getBoolean("ignoreUnknownKeys"),
        isLenient = getBoolean("isLenient"),
        allowStructuredMapKeys = getBoolean("allowStructuredMapKeys"),
        prettyPrint = getBoolean("prettyPrint"),
        explicitNulls = getBoolean("explicitNulls"),
        prettyPrintIndent = getString("prettyPrintIndent"),
        coerceInputValues = getBoolean("coerceInputValues"),
        useArrayPolymorphism = getBoolean("useArrayPolymorphism"),
        classDiscriminator = getString("classDiscriminator"),
        allowSpecialFloatingPointValues = getBoolean("allowSpecialFloatingPointValues"),
        useAlternativeNames = getBoolean("useAlternativeNames"),
        namingStrategy = null,
    )
})

class JsonProvider @Inject constructor(private val config: JsonSerializationConfig) : Provider<Json> {
    override fun get(): Json = Json {
        encodeDefaults = config.encodeDefaults
        ignoreUnknownKeys = config.ignoreUnknownKeys
        isLenient = config.isLenient
        allowStructuredMapKeys = config.allowStructuredMapKeys
        prettyPrint = config.prettyPrint
        explicitNulls = config.explicitNulls
        prettyPrintIndent = config.prettyPrintIndent
        coerceInputValues = config.coerceInputValues
        useArrayPolymorphism = config.useArrayPolymorphism
        classDiscriminator = config.classDiscriminator
        allowSpecialFloatingPointValues = config.allowSpecialFloatingPointValues
    }
}

object JsonSerializationModule : AbstractModule() {
    override fun configure() {
        bind(Json::class.java).toProvider(JsonProvider::class.java).asEagerSingleton()
    }
}
