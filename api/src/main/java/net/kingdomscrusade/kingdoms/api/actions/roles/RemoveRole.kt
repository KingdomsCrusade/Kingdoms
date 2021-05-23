package net.kingdomscrusade.kingdoms.api.actions.roles

import net.kingdomscrusade.kingdoms.api.actions.Commons
import net.kingdomscrusade.kingdoms.api.actions.IAction
import java.sql.Statement

class RemoveRole(private val roleName: String, private val roleKingdom: String): IAction, Commons() {
    override fun execute(statement: Statement): String {
        val roleUUID = getRoleUUID(roleName, roleKingdom, statement).get()
        statement.executeUpdate("DELETE FROM Roles WHERE role_uuid = '$roleUUID';")
        return roleUUID.toString()
    }
}