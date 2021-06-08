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
    var id : UUID?,
    var name: String?,
    var permissions: Set<PermissionType>?,
    var kingdomId: UUID?,
) : ApiModels {
    constructor(name: String?, kingdomId: UUID?) : this(id = null, name, permissions = null, kingdomId)
}

