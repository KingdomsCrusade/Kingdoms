@file:Suppress("DataClassPrivateConstructor", "unused")

package net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target

import java.util.*

data class RolesTarget private constructor(
    val type: TargetType,
    val id: UUID?,
    val name: String?,
    val kingdom: UUID?
) : ApiTarget {
    constructor(id: UUID, kingdom: UUID?) : this(type = TargetType.ID, id = id, name = null, kingdom = kingdom)
    constructor(name: String, kingdom: UUID?) : this(type = TargetType.NAME, id = null, name = name, kingdom = kingdom)
}
