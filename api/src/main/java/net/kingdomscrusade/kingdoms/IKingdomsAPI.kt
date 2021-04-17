package net.kingdomscrusade.kingdoms

import java.sql.Connection
import java.sql.ResultSet

interface IKingdomsAPI {

    var database: Connection

    /* General Operations */
    fun executeQuery(query: String): ResultSet
    fun executeUpdate(update:String): Int
    fun disconnect()
    fun connect()
    fun isConnected(): Boolean

    /* Prepared Operations */
    fun getActions(): IActions

}