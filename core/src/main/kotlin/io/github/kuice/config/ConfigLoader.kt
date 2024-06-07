package io.github.kuice.config

import com.typesafe.config.Config
import jakarta.inject.Provider

abstract class ConfigLoader<T>(private val prefix: String, val f: Config.() -> T) {
    fun load(config: Config): T = f(config.getConfig(prefix))
}

abstract class ConfigProvider<T>(private val loader: ConfigLoader<T>, private val config: Config) : Provider<T> {
    override fun get(): T = loader.load(config)
}

fun Config.getClass(path: String): Class<*> {
    val clazzName = getString(path)
    return Class.forName(clazzName)
}

inline fun <reified T> Config.getInstance(path: String): T {
    val clazzName = getString(path)
    val clazz = Class.forName(clazzName)

    if (clazz is T) {
        return clazz
    } else {
        throw Exception("Unable to get class.")
    }
}

fun <T> Config.getOptional(path: String, getValue: (String) -> T): T? {
    return if (hasPath(path)) {
        getValue(path)
    } else {
        null
    }
}

fun Config.getOptionalLong(path: String): Long? = getOptional(path, ::getLong)
