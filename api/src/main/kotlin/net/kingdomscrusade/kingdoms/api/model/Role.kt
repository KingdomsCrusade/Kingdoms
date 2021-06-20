package net.kingdomscrusade.kingdoms.api.model

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*


data class Role(
    override var id : UUID? = UUID.randomUUID(),
    override var name: String?,
    var permissions: MutableSet<PermissionType>?,
    var kingdom: UUID?,
) : ApiModels {
    constructor(id: UUID) : this(id = id, name = null, permissions = null, kingdom = null)
    constructor(name: String, kingdom: UUID?) : this(id = null, name = name, permissions = null, kingdom = kingdom)
    constructor(name: String?, permissions: MutableSet<PermissionType>?, kingdom: UUID?) : this(
        id = null,
        name = name,
        permissions = permissions,
        kingdom = kingdom
    )
}

