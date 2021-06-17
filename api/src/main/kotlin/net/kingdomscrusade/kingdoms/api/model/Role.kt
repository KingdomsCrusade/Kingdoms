package net.kingdomscrusade.kingdoms.api.model

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*


data class Role(
    override var id : UUID? = UUID.randomUUID(),
    override var name: String?,
    var permissions: Set<PermissionType>?,
    var kingdomId: UUID?,
) : ApiModels {
    constructor(name: String?, kingdomId: UUID?) : this(name = name, permissions = null, kingdomId = kingdomId)
    constructor() : this(name = null, permissions = null, kingdomId = null)
}

