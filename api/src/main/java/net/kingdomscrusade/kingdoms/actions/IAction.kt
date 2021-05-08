package net.kingdomscrusade.kingdoms.actions

import java.sql.Connection

interface IAction {
    fun execute(database: Connection): String
}