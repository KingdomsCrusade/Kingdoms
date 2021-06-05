package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class User(
    val userId : UUID,
    val userName: String,
    val kingdomId: UUID,
    val roleId: UUID,
)
