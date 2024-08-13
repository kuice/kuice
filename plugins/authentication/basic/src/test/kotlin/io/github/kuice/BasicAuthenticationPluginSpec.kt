package io.github.kuice

import com.google.inject.Inject
import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.Principal
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.UserPasswordCredential
import io.ktor.server.response.respond

class BasicAuthenticationPluginSpec : WordSpec({
    "The basic authentication plugin" should {

        "return 401 (Unauthorized) if no credentials are provided" {
            testApplication {
                routes {
                    get("endpoint") { call.respond("Welcome") }
                }

                val response = client.get("/endpoint")
                response.status shouldBe HttpStatusCode.Unauthorized
            }
        }

        "return 401 (Unauthorized) if provided credentials are invalid" { }

        "return 200 (Ok) if provided credentials are valid" { }
    }
})

class SimpleValidator @Inject constructor() : CredentialValidator {
    override suspend fun invoke(call: ApplicationCall, credentials: UserPasswordCredential): Principal? =
        if (credentials.name == "test" && credentials.password == "test") {
            UserIdPrincipal(credentials.name)
        } else {
            null
        }
}
