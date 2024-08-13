rootProject.name = "kuice"

include("core")
project(":core").name = "kuice-core"

include("test")
project(":test").name = "kuice-test"

// Web Socket Plugins
include("plugins:websocket:core")
project(":plugins:websocket:core").name = "kuice-websocket-core"

include("plugins:websocket:json")
project(":plugins:websocket:json").name = "kuice-websocket-json"

// Serialization Plugins
include("plugins:serialization:json")
project(":plugins:serialization:json").name = "kuice-serialization-json"

// Authentication Plugins
include("plugins:authentication:core")
project(":plugins:authentication:core").name = "kuice-authentication-core"

include("plugins:authentication:basic")
project(":plugins:authentication:basic").name = "kuice-authentication-basic"

include("plugins:authentication:jwt")
project(":plugins:authentication:jwt").name = "kuice-authentication-jwt"
