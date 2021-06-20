package net.kingdomscrusade.kingdoms.api.entrypoint

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.CreateStatement
import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.CreateStatement.AddedModel
import net.kingdomscrusade.kingdoms.api.model.Role

fun CreateStatement.role (values : Role.() -> Unit) : AddedModel =
    Role().let {
        it.values()
        models += it
        AddedModel(it)
    }

