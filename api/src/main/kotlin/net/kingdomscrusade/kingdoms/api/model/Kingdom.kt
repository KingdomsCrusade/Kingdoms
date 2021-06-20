package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class Kingdom(
    override var id: UUID? = UUID.randomUUID(),
    override var name: String?,
) : ApiModels {
    constructor() : this(name = null)
}