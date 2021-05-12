package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Statement

class RemoveRole(private val roleName: String, private val roleKingdom: String): IAction, Commons() {
    override fun execute(statement: Statement): String {
        val roleUUID = getRoleUUID(roleName, roleKingdom, statement).get()
        statement.executeUpdate("DELETE FROM Roles WHERE role_uuid = '$roleUUID';")
        return roleUUID.toString()
    }
}