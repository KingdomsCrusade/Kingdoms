package net.kingdomscrusade.kingdoms.api.`interface`

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels

infix fun KingdomsApi.create (init : CreateStatement.() -> Unit) : CreateStatement { TODO() }
infix fun KingdomsApi.read (init : ReadStatement.() -> Unit) : ReadStatement { TODO() }
infix fun KingdomsApi.update (init : UpdateStatement.() -> Unit) : UpdateStatement { TODO() }
infix fun KingdomsApi.delete (init : DeleteStatement.() -> Unit) : DeleteStatement { TODO() }

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

