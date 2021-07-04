package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class KingdomsModel(
    override val id: UUID = UUID.randomUUID(),
    override val name: String,
) : ApiModel