package net.kingdomscrusade.kingdoms.api.actions

import java.sql.Statement

interface IAction {
    fun execute(statement: Statement): String
}