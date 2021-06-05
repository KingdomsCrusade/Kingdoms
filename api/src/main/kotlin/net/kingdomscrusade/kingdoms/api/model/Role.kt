package net.kingdomscrusade.kingdoms.api.model

import java.util.*


enum class PermissionType {
    ADMIN,
    MANAGE,
    PICK,
    CONTAINER,
    INTERACT,
    BUILD,
    KILL,
    TALK
}

data class Role(
    val roleId : UUID,
    val roleName: String,
    val rolePermissions: Set<PermissionType>,
    val kingdomId: UUID,
)
