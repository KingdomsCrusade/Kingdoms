package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels

fun KingdomsApi.delete(init: DeleteStatement.() -> Unit): DeleteStatement {
    TODO()
}

class DeleteStatement {
    // TODO: 8/6/2021
    var target: ApiModels? = null
}