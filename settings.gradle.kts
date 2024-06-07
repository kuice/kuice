rootProject.name = "kuice"

include("core")
include("test")

// Web Socket Plugins
include("plugins:websocket:core")
project(":plugins:websocket:core").name = "websocket-core"

include("plugins:websocket:json")
project(":plugins:websocket:json").name = "websocket-json"

// Serialization Plugins
include("plugins:serialization:json")
project(":plugins:serialization:json").name = "serialization-json"

// Authentication Plugins
include("plugins:authentication:core")
project(":plugins:authentication:core").name = "authentication-core"

include("plugins:authentication:basic")
project(":plugins:authentication:basic").name = "authentication-basic"

include("plugins:authentication:jwt")
project(":plugins:authentication:jwt").name = "authentication-jwt"

// Examples
include("examples:basic")
include("examples:nested-routes")
include("examples:authentication:basic")
include("examples:authentication:jwt")
include("examples:websocket")
