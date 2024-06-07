package io.github.kuice

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.google.inject.Inject
import com.google.inject.ProvidedBy
import com.typesafe.config.Config
import io.github.kuice.config.ConfigLoader
import io.github.kuice.config.ConfigProvider
import io.github.kuice.ktor.plugins.BaseApplicationPlugin
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

class JwtAuthenticationPlugin @Inject constructor(config: JwtAuthenticationConfig) :
    BaseApplicationPlugin<AuthenticationConfig, Authentication>(
        Authentication,
        {
            jwt {
                realm = config.realm
                verifier(
                    JWT
                        .require(Algorithm.HMAC256(config.secret))
                        .withAudience(config.audience)
                        .withIssuer(config.issuer)
                        .build(),
                )
                validate { credential ->
                    JWTPrincipal(credential.payload)
                }
            }
        },
    )

@ProvidedBy(JwtAuthenticationConfigProvider::class)
data class JwtAuthenticationConfig(
    val realm: String,
    val audience: String,
    val issuer: String,
    val secret: String,
)

class JwtAuthenticationConfigProvider @Inject constructor(config: Config) :
    ConfigProvider<JwtAuthenticationConfig>(JwtAuthenticationConfigurationLoader, config)

object JwtAuthenticationConfigurationLoader :
    ConfigLoader<JwtAuthenticationConfig>(
        "ktor.authentication.jwt",
        {
            JwtAuthenticationConfig(
                realm = getString("realm"),
                audience = getString("audience"),
                issuer = getString("issuer"),
                secret = getString("secret"),
            )
        },
    )
