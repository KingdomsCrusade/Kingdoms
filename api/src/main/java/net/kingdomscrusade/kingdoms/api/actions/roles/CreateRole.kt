package net.kingdomscrusade.kingdoms.api.actions.roles

import net.kingdomscrusade.kingdoms.api.actions.Commons
import net.kingdomscrusade.kingdoms.api.actions.IAction
import net.kingdomscrusade.kingdoms.api.types.Permissions
import java.sql.Statement
import java.util.*

class CreateRole
    (
    private val roleUUID: UUID = UUID.randomUUID(),
    private val roleName: String,
    private val roleKingdom: String,
    private val rolePermissions: Set<Permissions>
    ): IAction, Commons()
{

    override fun execute(statement: Statement): String {
        if (checkRoleDuplicate(roleName, roleKingdom, statement))
            throw IllegalArgumentException("Duplicated name found under the same kingdom.")
        statement.executeUpdate(
            """
                INSERT INTO Roles VALUES (
                    '$roleUUID', 
                    '$roleName', 
                    '${getKingdomUUID(roleKingdom, statement).get()}', 
                    '${permissionToString(rolePermissions)}'
                );
            """.trimIndent()
        )
        return roleUUID.toString()
    }
}