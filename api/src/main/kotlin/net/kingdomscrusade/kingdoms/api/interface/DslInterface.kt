package net.kingdomscrusade.kingdoms.api.`interface`

import net.kingdomscrusade.kingdoms.api.model.ApiModels

fun create (init : CreateStatement.() -> Unit) : CreateStatement { TODO() }
fun read (init : ReadStatement.() -> Unit) : ReadStatement { TODO() }
fun update (init : UpdateStatement.() -> Unit) : UpdateStatement { TODO() }
fun delete (init : DeleteStatement.() -> Unit) : DeleteStatement { TODO() }

class CreateStatement {
    // TODO: 8/6/2021
}

class ReadStatement {
    // TODO: 8/6/2021
    var target : ApiModels? = null
}

class UpdateStatement {
    // TODO: 8/6/2021
    var target : ApiModels? = null
    var change : ApiModels? = null
}


class DeleteStatement {
    // TODO: 8/6/2021
    var target : ApiModels? = null
}

