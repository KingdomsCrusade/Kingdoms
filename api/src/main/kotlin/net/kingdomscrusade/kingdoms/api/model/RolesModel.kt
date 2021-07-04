package net.kingdomscrusade.kingdoms.api.model

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*


data class RolesModel(
    override val id : UUID = UUID.randomUUID(),
    override val name: String,
    val permissions: MutableSet<PermissionType> = mutableSetOf(),
    val kingdom: UUID?,
) : ApiModel

