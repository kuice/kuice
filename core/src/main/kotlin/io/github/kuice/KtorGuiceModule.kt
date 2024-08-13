package io.github.kuice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.typesafe.config.Config
import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.engine.ApplicationEngine

class KtorGuiceModule(private val config: Config) : AbstractModule() {

    override fun configure() {
        bind(ApplicationEngine::class.java).toProvider(ApplicationEngineProvider::class.java).asEagerSingleton()
    }

    @Provides
    fun provideConfig(): Config = config

    @Provides
    fun provideApplicationConfig(application: Application): ApplicationConfig = application.environment.config

    @Provides
    fun provideApplication(engine: ApplicationEngine): Application = engine.application
}
