package net.kingdomscrusade.kingdoms.api.model

import java.util.*

data class KingdomsModel(
    var id: UUID = UUID.randomUUID(),
    var name: String,
) : ApiModel