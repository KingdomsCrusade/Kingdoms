package net.kingdomscrusade.kingdoms

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

interface IDatabase {

    var database: Connection

    /* General Operations */

    /**
     * Executes any query command to the database.
     *
     * @param[query] An SQL query string
     * @return A ResultSet object that contains the queries
     */
    fun executeQuery(query: String): ResultSet =
        database.createStatement().executeQuery(query)

    /**
     * Executes any update command to the database.
     *
     * @param[update] An SQL query string
     * @return Number of rows updated
     */
    fun executeUpdate(update:String): Int =
        database.createStatement().executeUpdate(update)

    fun prepareStatement(statement: String): PreparedStatement =
        database.prepareStatement(statement)

    fun disconnect(){
        database.close()
    }

    /**
     * Returns a boolean indicating the database connection status.
     */
    fun isConnected(): Boolean =
        !database.isClosed

}