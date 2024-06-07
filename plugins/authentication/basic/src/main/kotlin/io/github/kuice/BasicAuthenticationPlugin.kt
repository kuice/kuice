package io.github.kuice

import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.ProvidedBy
import com.typesafe.config.Config
import io.github.kuice.config.ConfigLoader
import io.github.kuice.config.ConfigProvider
import io.github.kuice.config.getClass
import io.github.kuice.ktor.plugins.BaseApplicationPluginWithInjector
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.Principal
import io.ktor.server.auth.UserPasswordCredential
import io.ktor.server.auth.basic
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("io.github.kuice.authentication.basic")
class BasicAuthenticationPlugin @Inject constructor(config: BasicAuthenticationConfig) :
    BaseApplicationPluginWithInjector<AuthenticationConfig, Authentication>(
        Authentication,
        { injector -> configure(injector, config) },
    )

private fun configure(injector: Injector, config: BasicAuthenticationConfig): AuthenticationConfig.() -> Unit = {
    val validator = injector.getInstance(config.validator)

    if (validator is CredentialValidator) {
        basic {
            realm = config.realm
            validate { credentials -> validator(this, credentials) }
        }
    } else {
        logger.error("Validator ${config.validator.simpleName} is not an instance CredentialValidator")
    }
}

@ProvidedBy(BasicAuthenticationConfigProvider::class)
data class BasicAuthenticationConfig(
    val realm: String,
    val validator: Class<*>,
)

class BasicAuthenticationConfigProvider @Inject constructor(config: Config) :
    ConfigProvider<BasicAuthenticationConfig>(BasicAuthenticationConfigLoader, config)

object BasicAuthenticationConfigLoader :
    ConfigLoader<BasicAuthenticationConfig>(
        "ktor.authentication.basic",
        {
            BasicAuthenticationConfig(
                realm = getString("realm"),
                validator = getClass("validator"),
            )
        },
    )

interface CredentialValidator {
    suspend operator fun invoke(call: ApplicationCall, credentials: UserPasswordCredential): Principal?
}
