package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class UsersModel(
    var id : UUID,
    var name: String,
    var kingdom: UUID,
    var role: UUID?,
) : ApiModel
