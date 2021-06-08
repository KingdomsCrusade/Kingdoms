package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class User(
    var id : UUID?,
    var name: String?,
    var kingdomId: UUID?,
    var roleId: UUID?,
) : ApiModels {
    constructor(name: String?) : this(id = null, name, kingdomId = null, roleId = null)
}

