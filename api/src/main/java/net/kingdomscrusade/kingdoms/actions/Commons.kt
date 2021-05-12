package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.KingdomsAPI
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
                val query = statement.executeQuery("SELECT role_uuid FROM Roles WHERE role_kingdom = $roleKingdom")
                if (query.next())
                    Optional.of(
                        UUID.fromString(query.getString("role_uuid"))
                    )
                else
                    Optional.empty()
            }
        }
    }
}