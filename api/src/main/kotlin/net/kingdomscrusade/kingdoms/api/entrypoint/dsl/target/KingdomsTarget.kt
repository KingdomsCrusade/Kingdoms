@file:Suppress("DataClassPrivateConstructor", "unused")

package net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target

import java.util.*

data class KingdomsTarget private constructor(
    override val type: TargetType,
    override val id: UUID?,
    override val name: String?
) : ApiTarget {
    constructor(id: UUID) : this(type = TargetType.ID, id = id, name = null)
    constructor(name: String) : this(type = TargetType.NAME, id = null, name = name)
}

