package net.kingdomscrusade.kingdoms.actions

import java.sql.Statement

interface IAction {
    fun execute(statement: Statement): String
}