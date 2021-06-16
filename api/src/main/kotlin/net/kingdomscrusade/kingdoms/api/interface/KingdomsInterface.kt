package net.kingdomscrusade.kingdoms.api.`interface`

import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.`interface`.CreateStatement.AddedModel

fun CreateStatement.kingdom (values : Kingdom.() -> Unit) : AddedModel =
    Kingdom().let {
        it.values()
        models += it
        AddedModel(it)
    }
