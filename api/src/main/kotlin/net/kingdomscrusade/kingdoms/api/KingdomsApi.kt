package net.kingdomscrusade.kingdoms.api

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.kingdomscrusade.kingdoms.api.KingdomsApiManager as manager
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

class KingdomsApi private constructor() {
    companion object {
        fun connect(url: String, usr: String?, pwd: String?) {
            HikariDataSource(
                HikariConfig().apply {
                    jdbcUrl = url
                    username = usr
                    password = pwd
                }
            ).let {
                Database.connect(it)
                Flyway.configure()
                    .dataSource(it).load()
                    .migrate()
                manager.set(KingdomsApi())
            }
        }
        fun get() = manager.get()
    }
}

