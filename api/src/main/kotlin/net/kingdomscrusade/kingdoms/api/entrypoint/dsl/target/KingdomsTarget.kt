@file:Suppress("DataClassPrivateConstructor", "unused")

package net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target

import java.util.*

data class KingdomsTarget private constructor(
    val type: TargetType,
    val id: UUID?,
    val name: String?
) : ApiTarget {
    constructor(id: UUID) : this(type = TargetType.ID, id = id, name = null)
    constructor(name: String) : this(type = TargetType.NAME, id = null, name = name)
}

