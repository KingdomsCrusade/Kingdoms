package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class User(
    override var id : UUID?,
    override var name: String?,
    var kingdom: UUID?,
    var role: UUID?,
) : ApiModels {
    constructor(id: UUID, kingdom: UUID) : this(id = id, name = null, kingdom = kingdom, role = null)

    // Couldn't add name & kingdom focused constructor due to conflicting overload
    constructor(name: String?, role: UUID?) : this(id = null, name = name, kingdom = null, role = role)
}

