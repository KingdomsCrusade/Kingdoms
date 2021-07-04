package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class UsersModel(
    override val id : UUID,
    override val name: String,
    val kingdom: UUID,
    val role: UUID?,
) : ApiModel
