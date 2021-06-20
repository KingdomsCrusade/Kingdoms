package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class User(
    override var id : UUID?,
    override var name: String?,
    var kingdom: UUID?,
    var role: UUID?,
) : ApiModels {
    constructor() : this(null, null, null, null)
}

