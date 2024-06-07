package io.github.kuice

import com.google.inject.Inject
import com.google.inject.ProvidedBy
import com.google.inject.Provider
import com.typesafe.config.Config
import io.github.kuice.config.ConfigLoader
import io.github.kuice.config.ConfigProvider
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineFactory
import io.ktor.server.engine.embeddedServer

class ApplicationEngineProvider @Inject constructor(
    private val config: ApplicationConfiguration,
) : Provider<ApplicationEngine> {
    override fun get(): ApplicationEngine {
        val engineFactory = getEngineFactory(config.engine)

        return embeddedServer(engineFactory, port = config.port, host = config.host) {}
    }

    private fun getEngineFactory(engineName: String): ApplicationEngineFactory<*, *> {
        val clazz = Class.forName(engineName).kotlin.objectInstance

        if (clazz is ApplicationEngineFactory<*, *>) {
            return clazz
        } else {
            throw Exception("Unable to resolve engine with qualified name $engineName")
        }
    }
}

@ProvidedBy(ApplicationConfigProvider::class)
data class ApplicationConfiguration(
    val engine: String,
    val host: String,
    val port: Int,
)

class ApplicationConfigProvider @Inject constructor(config: Config) :
    ConfigProvider<ApplicationConfiguration>(ApplicationConfigLoader, config)

object ApplicationConfigLoader :
    ConfigLoader<ApplicationConfiguration>("ktor", {
        ApplicationConfiguration(
            engine = getString("engine"),
            host = getString("host"),
            port = getInt("port"),
        )
    })
