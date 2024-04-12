rootProject.name = "kuice"

include("core")

// Serialization Plugins
include("plugins:serialization:json")
project(":plugins:serialization:json").name = "serialization-json"

// Authentication Plugins
include("plugins:authentication:core")
project(":plugins:authentication:core").name = "authentication-core"

include("plugins:authentication:basic")
project(":plugins:authentication:basic").name = "authentication-basic"
