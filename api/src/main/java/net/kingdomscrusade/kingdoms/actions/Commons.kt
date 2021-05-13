package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.KingdomsAPI
import java.security.Permissions
import java.sql.Statement
import java.util.*

open class Commons {
    /**
     * Gets UUID of a kingdom by its name, assuming that
     * the name only appears once in the database and the
     * kingdom_name column is not null.
     */
    protected val getKingdomUUID: (kingdomName: String, statement: Statement) -> Optional<UUID> = { kingdomName: String, statement: Statement ->
        val query = statement.executeQuery("SELECT kingdom_uuid FROM Kingdoms WHERE kingdom_name = '$kingdomName';")
        if (query.next())
            Optional.of(
                UUID.fromString(query.getString("kingdom_uuid"))
            )
        else
            Optional.empty()
    }

    /**
     * Gets UUID of a role by its name and kingdom, assuming that
     * the name only appears once in the database and the
     * role_name and role_kingdom column is not null.
     */
    protected val getRoleUUID: (roleName:String, roleKingdom:String, statement: Statement) -> Optional<UUID> = { roleName: String, roleKingdom: String, statement: Statement ->
        when (roleName) {
            "Owner"     -> Optional.of(UUID.fromString(KingdomsAPI.ownerUUID))
            "Member"    -> Optional.of(UUID.fromString(KingdomsAPI.memberUUID))
            "Visitor"   -> Optional.of(UUID.fromString(KingdomsAPI.visitorUUID))
            else        -> {
                val query = statement.executeQuery("""
                    SELECT role_uuid FROM Roles WHERE (
                        role_kingdom = '${getKingdomUUID(roleKingdom, statement).get()}', 
                        role_name = '$roleName'
                    );
                        """.trimIndent()
                )
                if (query.next())
                    Optional.of(
                        UUID.fromString(query.getString("role_uuid"))
                    )
                else
                    Optional.empty()
            }
        }
    }

    /**
     * Returns a boolean stating if a duplicate of
     * the role name is found in the kingdom.
     */
    protected val checkRoleDuplicate: (roleName: String, roleKingdom: String, statement: Statement) -> Boolean = {roleName: String, roleKingdom: String, statement: Statement ->
        statement.executeQuery(
            """
                SELECT * FROM Roles 
                WHERE (
                    role_name = '$roleName', 
                    role_kingdom = '${getKingdomUUID(roleKingdom, statement).get()}'
                );
            """.trimIndent()
        ).next()
    }

    /**
     * Converts a Permissions set to a clean
     * string (To string without spaces and brackets).
     */
    protected val permissionsToCleanString: (Set<Permissions>) -> String = { permissionsSet: Set<Permissions> ->
        val builder = StringBuilder()
        permissionsSet.forEachIndexed{index: Int, permissions: Permissions ->
            if (index != 0) builder.append(",")
            builder.append(permissions.toString())
        }
        builder.toString()
    }

    /**
     * Gets Role Permissions string by its role UUID.
     */
    protected val getPermissions: (roleUUID: UUID, statement: Statement) -> Optional<String> = {roleUUID: UUID, statement: Statement ->
        val query = statement.executeQuery(
            """
                SELECT role_permissions FROM Roles WHERE role_uuid = '$roleUUID'
            """.trimIndent()
        )
        if (query.next())
            Optional.ofNullable(query.getString("role_permissions"))
        else
            Optional.empty()
    }
}