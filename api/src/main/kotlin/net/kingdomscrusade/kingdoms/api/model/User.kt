package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class User(
    override var id : UUID?,
    override var name: String?,
    var kingdomId: UUID?,
    var roleId: UUID?,
) : ApiModels {
    constructor(id: UUID) : this(id = id, name = null, kingdomId = null, roleId = null)
    constructor() : this(null, null, null, null)
}

