package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels

fun KingdomsApi.update(init: UpdateStatement.() -> Unit): UpdateStatement {
    TODO()
}

class UpdateStatement {
    // TODO: 8/6/2021
    var target: ApiModels? = null
    var change: ApiModels? = null
}