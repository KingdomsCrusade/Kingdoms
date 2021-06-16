package net.kingdomscrusade.kingdoms.api.`interface`

import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.`interface`.CreateStatement.AddedModel

fun CreateStatement.role (values : Role.() -> Unit) : AddedModel =
    Role().let {
        it.values()
        models += it
        AddedModel(it)
    }

