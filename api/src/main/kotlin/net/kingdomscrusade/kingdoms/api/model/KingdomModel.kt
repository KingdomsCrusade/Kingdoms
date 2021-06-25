package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class KingdomModel(
    var id: UUID = UUID.randomUUID(),
    var name: String,
) : ApiModel