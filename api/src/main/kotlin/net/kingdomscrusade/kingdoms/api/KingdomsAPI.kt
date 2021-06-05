package net.kingdomscrusade.kingdoms.api

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object KingdomsAPI {

    fun connect(url: String, usr: String?, pwd: String?){
        val source = HikariDataSource(
            HikariConfig().apply {
                this.jdbcUrl = url
                this.username = usr
                this.password = pwd
            }
        )
        Database.connect(source)
        Flyway.configure()
            .dataSource(source).load()
            .migrate()
    }

}

