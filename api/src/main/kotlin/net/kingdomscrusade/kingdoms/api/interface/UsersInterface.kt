package net.kingdomscrusade.kingdoms.api.`interface`

import net.kingdomscrusade.kingdoms.api.model.User
import net.kingdomscrusade.kingdoms.api.`interface`.CreateStatement.AddedModel

fun CreateStatement.user (values : User.() -> Unit) : AddedModel =
    User().let {
        it.values()
        models += it
        AddedModel(it)
    }
