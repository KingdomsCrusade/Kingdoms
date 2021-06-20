package net.kingdomscrusade.kingdoms.api.entrypoint

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.CreateStatement
import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.CreateStatement.AddedModel
import net.kingdomscrusade.kingdoms.api.model.User

fun CreateStatement.user (values : User.() -> Unit) : AddedModel =
    User().let {
        it.values()
        models += it
        AddedModel(it)
    }
