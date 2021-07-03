package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class UsersModel(
    val id : UUID,
    val name: String,
    val kingdom: UUID,
    val role: UUID?,
) : ApiModel
