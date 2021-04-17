package net.kingdomscrusade.kingdoms

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

/**
 * The main class of kingdoms API. A database will be created on class construction
 *
 * @param[dbFilePath] The file path of your database. Must end with a database file name.
 */
class KingdomsAPI(private val dbFilePath: String): IKingdomsAPI{

    override lateinit var database: Connection

    init {
        val databaseFile = File(dbFilePath)
        if (!databaseFile.exists()){
            connect()
            createDatabaseStructure()
        }
        else connect()
    }

    private fun createDatabaseStructure() {
        val statement = database.createStatement()
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

    /**
     * Executes any update command to the database.
     *
     * @param[update] An SQL query string
     * @return Number of rows updated
     */
    override fun executeUpdate(update: String): Int =
        database.createStatement().executeUpdate(update)

    /**
     * Executes any query command to the database.
     *
     * @param[query] An SQL query string
     * @return A ResultSet object that contains the queries
     */
    override fun executeQuery(query: String): ResultSet =
        database.createStatement().executeQuery(query)

    /**
     * Establishes connection with database.
     * This method should only be used during class construction.
     */
    override fun connect() {
        database = DriverManager.getConnection("jdbc:sqlite:$dbFilePath")
    }

    /**
     * Ends connection with database.
     */
    override fun disconnect() {
        database.close()
    }

    /**
     * Returns a boolean indicating the database connection status.
     */
    override fun isConnected(): Boolean =
        !database.isClosed

    override fun getActions(): IActions =
        Actions(this)
}