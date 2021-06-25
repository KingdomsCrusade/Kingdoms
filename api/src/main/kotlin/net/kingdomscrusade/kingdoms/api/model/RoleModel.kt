package net.kingdomscrusade.kingdoms.api.model

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import java.util.*


data class RoleModel(
    var id : UUID = UUID.randomUUID(),
    var name: String,
    var permissions: MutableSet<PermissionType> = mutableSetOf(),
    var kingdom: UUID?,
) : ApiModel

