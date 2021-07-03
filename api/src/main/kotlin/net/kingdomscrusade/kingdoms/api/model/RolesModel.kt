package net.kingdomscrusade.kingdoms.api.model

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*


data class RolesModel(
    val id : UUID = UUID.randomUUID(),
    val name: String,
    val permissions: MutableSet<PermissionType> = mutableSetOf(),
    val kingdom: UUID?,
) : ApiModel

