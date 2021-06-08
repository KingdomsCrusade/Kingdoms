package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class Kingdom(
    var id: UUID?,
    var name: String?,
) : ApiModels {
    constructor(name: String?) : this(id = null, name)
}