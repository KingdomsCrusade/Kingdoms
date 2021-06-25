package net.kingdomscrusade.kingdoms.api

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.CreateStatement
import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.DeleteStatement
import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.ReadStatement
import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.ReplaceStatement
import net.kingdomscrusade.kingdoms.api.model.ApiModel
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import java.sql.Connection
import java.util.*

class KingdomsApi private constructor(private val dataSource: HikariDataSource) {

    companion object {
        fun connect(url: String, usr: String?, pwd: String?): KingdomsApi {
            val dataSource =
                HikariDataSource(
                    HikariConfig().apply {
                        jdbcUrl = url
                        username = usr
                        password = pwd
                    }
                ).also {
                    Database.connect(it)
                    Flyway.configure()
                        .dataSource(it).load()
                        .migrate()
                }
            return KingdomsApi(dataSource)
        }
        fun connectWithoutMigrate (url : String, usr: String?, pwd: String?): KingdomsApi {
            val dataSource =
                HikariDataSource(
                    HikariConfig().apply {
                        jdbcUrl = url
                        username = usr
                        password = pwd
                    }
                ).also { Database.connect(it) }
            return KingdomsApi(dataSource)
        }
    }

    // Datasource
    fun isConnected(): Boolean = dataSource.isRunning
    fun getConnectionObject(): Connection = dataSource.connection

    // CRUDs
    fun create(init: CreateStatement.() -> Unit): List<UUID> =
        CreateStatement().let {
            it.init()
            it.execute()
        }

    fun read(init: ReadStatement.() -> Unit): List<ApiModel> =
        ReadStatement().let {
            it.init()
            it.execute()
        }

    fun replace(init: ReplaceStatement.() -> Unit) =
        ReplaceStatement().let {
            it.init()
            it.execute()
        }

    fun delete(init: DeleteStatement.() -> Unit) =
        DeleteStatement().let {
            it.init()
            it.execute()
        }
}

