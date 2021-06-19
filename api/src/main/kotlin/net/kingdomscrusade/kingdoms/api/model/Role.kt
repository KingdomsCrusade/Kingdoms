package net.kingdomscrusade.kingdoms.api.model

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*


data class Role(
    override var id : UUID? = UUID.randomUUID(),
    override var name: String?,
    var permissions: MutableSet<PermissionType>?,
    var kingdom: UUID?,
) : ApiModels {
//    constructor(name: String?, kingdomId: UUID?) : this(name = name, permissions = null, kingdom = kingdomId)
    constructor() : this(name = null, permissions = null, kingdom = null)
}

