package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class Kingdom(
    override var id: UUID? = UUID.randomUUID(),
    override var name: String?,
) : ApiModels {
    constructor(id: UUID) : this(id, name = null)
//    constructor(name: String) : this(id = null, name = name) // Disabled due to constructor conflicts
}