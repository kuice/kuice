package com.ordina.example

import com.google.inject.Inject
import com.ordina.kuice.CredentialValidator
import com.typesafe.config.Config
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.Principal
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.UserPasswordCredential

class BasicCredentialValidator @Inject constructor(config: Config) :
    CredentialValidator {
    private val username = config.getString("example.username")
    private val password = config.getString("example.password")
    override suspend fun invoke(call: ApplicationCall, credentials: UserPasswordCredential): Principal? =
        if (credentials.name == username && credentials.password == password) {
            UserIdPrincipal(username)
        } else {
            null
        }
}