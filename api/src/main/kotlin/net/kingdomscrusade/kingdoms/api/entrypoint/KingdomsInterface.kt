package net.kingdomscrusade.kingdoms.api.entrypoint

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.CreateStatement
import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.CreateStatement.AddedModel
import net.kingdomscrusade.kingdoms.api.model.Kingdom

fun CreateStatement.kingdom (values : Kingdom.() -> Unit) : AddedModel =
    Kingdom().let {
        it.values()
        models += it
        AddedModel(it)
    }
