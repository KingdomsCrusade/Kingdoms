package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels

fun KingdomsApi.read(init: ReadStatement.() -> Unit): ReadStatement {
    TODO()
}

class ReadStatement {
    // TODO: 8/6/2021
    var target: ApiModels? = null
}