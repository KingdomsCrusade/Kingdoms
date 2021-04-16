package net.kingdomscrusade.kingdoms

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class KingdomsAPI(dbFilePath: String) {

    private val databaseConnection: Connection

    init {
        val databaseFile = File(dbFilePath)
        if (!databaseFile.exists()){
            databaseConnection = DriverManager.getConnection("jdbc:sqlite:$dbFilePath")
            createDatabaseStructure()
        } else databaseConnection = DriverManager.getConnection("jdbc:sqlite:$dbFilePath")
    }

    private fun createDatabaseStructure() {
        val statement = databaseConnection.createStatement()
        statement.executeUpdate(
            """
                CREATE TABLE Kingdoms (
                    kingdom_uuid STRING (36) PRIMARY KEY
                                             NOT NULL
                                             UNIQUE,
                    kingdom_name STRING (12) UNIQUE
                                             NOT NULL
                );
                CREATE TABLE Permissions (
                    permission_id   INTEGER PRIMARY KEY AUTOINCREMENT
                                            UNIQUE
                                            NOT NULL,
                    permission_flag STRING  NOT NULL,
                    permission_role         NOT NULL
                                            REFERENCES Roles (role_uuid) ON DELETE CASCADE
                                                                         ON UPDATE CASCADE
                );
                CREATE TABLE Roles (
                    role_uuid    STRING (36) PRIMARY KEY
                                             UNIQUE
                                             NOT NULL,
                    role_name    STRING (12) NOT NULL,
                    role_kingdom             REFERENCES Kingdoms ON DELETE CASCADE
                                                                 ON UPDATE CASCADE
                );
                CREATE TABLE Users (
                    user_uuid           STRING (36) UNIQUE
                                                    NOT NULL
                                                    PRIMARY KEY,
                    user_name           STRING (16) UNIQUE
                                                    NOT NULL,
                    user_kingdom                    REFERENCES Kingdoms (kingdom_uuid) ON DELETE CASCADE
                                                                                       ON UPDATE CASCADE
                                                    NOT NULL,
                    [user_kingdom:role]             REFERENCES Roles (role_uuid) ON DELETE SET NULL
                                                                                 ON UPDATE CASCADE
                );
            """.trimIndent()
        )
    }

    fun executeUpdate(update: String): Int =
        databaseConnection.createStatement().executeUpdate(update)

    fun executeQuery(query: String): ResultSet =
        databaseConnection.createStatement().executeQuery(query)

    fun closeConnection() {
        databaseConnection.close()
    }

    fun isClosed() =
        databaseConnection.isClosed
}