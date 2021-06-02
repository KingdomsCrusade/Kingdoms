package net.kingdomscrusade.kingdoms.api

import net.kingdomscrusade.kingdoms.api.tables.*
import net.kingdomscrusade.kingdoms.api.types.Permissions
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

internal class DatabaseUpgrades {
    val list = arrayOf( DatabaseUpgrades::upgradeFromV0 )
    private fun upgradeFromV0() {
        // Variables
        val ownerUUID = UUID.fromString(KingdomsAPI.owner)
        val memberUUID = UUID.fromString(KingdomsAPI.member)
        val visitorUUID = UUID.fromString(KingdomsAPI.visitor)
        val roles = mapOf<String, UUID>(
            "Owner" to ownerUUID,
            "Member" to memberUUID,
            "Visitor" to visitorUUID
        )
        val permissions = mapOf<UUID, Permissions>(
            ownerUUID to Permissions.ADMIN,
            memberUUID to Permissions.CONTAINER,
            memberUUID to Permissions.BUILD,
            memberUUID to Permissions.TALK,
            memberUUID to Permissions.PICK,
            memberUUID to Permissions.INTERACT,
            memberUUID to Permissions.KILL,
            visitorUUID to Permissions.INTERACT,
            visitorUUID to Permissions.TALK,
        )
        transaction {
            // Creating Tables
            SchemaUtils.create(Kingdoms, Roles, RoleToPermissions, Users, PluginInfo)
            // Inserting Roles
            for (role in roles)
                Roles.insert {
                    it[name] = role.key
                    it[uuid] = role.value
                }
            // Inserting Permissions
            for (permission in permissions)
                RoleToPermissions.insert {
                    it[ref] = permission.key
                    it[value] = permission.value
                }
        }
    }
}