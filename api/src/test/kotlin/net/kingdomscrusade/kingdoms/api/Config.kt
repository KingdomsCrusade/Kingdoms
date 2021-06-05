package net.kingdomscrusade.kingdoms.api

import com.typesafe.config.ConfigFactory

object Config {
    val url: String get() = ConfigFactory.load().getConfig("database").getString("url")
    val usr: String get() = ConfigFactory.load().getConfig("database").getString("usr")
    val pwd: String get() = ConfigFactory.load().getConfig("database").getString("pwd")
}