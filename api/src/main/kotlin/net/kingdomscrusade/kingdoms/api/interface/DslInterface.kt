package net.kingdomscrusade.kingdoms.api.`interface`

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.model.User
import java.util.*

infix fun KingdomsApi.create (init : CreateStatement.() -> Unit) : CreateStatement =
    CreateStatement().also {
        it.init()
        it.queriedIds = CreateStatement.AddedModel.idCache // Initializing its Id list
        CreateStatement.AddedModel.idCache = mutableListOf() // Clearing AddedModel's Id cache
    }

infix fun KingdomsApi.read (init : ReadStatement.() -> Unit) : ReadStatement { TODO() }
infix fun KingdomsApi.update (init : UpdateStatement.() -> Unit) : UpdateStatement { TODO() }
infix fun KingdomsApi.delete (init : DeleteStatement.() -> Unit) : DeleteStatement { TODO() }

class CreateStatement {
    val models : MutableList<ApiModels> = mutableListOf()
    lateinit var queriedIds : List<UUID>

    class AddedModel (private val model : ApiModels) {
        companion object { var idCache : MutableList<UUID> = mutableListOf() }
        fun getId() = model.id
            ?.also { idCache.add(it) }
            ?: throw IllegalStateException("No id exists in model")
    }

    fun execute() : List<UUID> {
        for (model in models)
            when (model) {
                is Kingdom -> println("Kingdom made") // TODO: 16/6/2021
                is Role -> println("Role made")
                is User -> println("User made")
            }
        return if (this::queriedIds.isInitialized)
            queriedIds
        else
            listOf()
    }

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

