rootProject.name = "kuice"

include("core")
// Web Socket Plugins
include("plugins:websocket:core")
project(":plugins:websocket:core").name = "websocket-core"
// Authentication Plugins
include("plugins:authentication:core")
project(":plugins:authentication:core").name = "authentication-core"

include("plugins:authentication:basic")
project(":plugins:authentication:basic").name = "authentication-basic"
