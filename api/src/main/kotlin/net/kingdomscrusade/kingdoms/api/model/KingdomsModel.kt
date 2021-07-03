package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class KingdomsModel(
    val id: UUID = UUID.randomUUID(),
    val name: String,
) : ApiModel