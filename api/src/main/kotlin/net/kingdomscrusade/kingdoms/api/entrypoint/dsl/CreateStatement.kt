package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.model.User
import java.util.*

fun KingdomsApi.create(init: CreateStatement.() -> Unit): CreateStatement =
    CreateStatement().also {
        it.init()
        it.queriedIds = CreateStatement.AddedModel.idCache // Initializing its Id list
        CreateStatement.AddedModel.idCache = mutableListOf() // Clearing AddedModel's Id cache
    }

class CreateStatement {
    val models: MutableList<ApiModels> = mutableListOf()
    lateinit var queriedIds: List<UUID>

    class AddedModel(private val model: ApiModels) {
        companion object {
            var idCache: MutableList<UUID> = mutableListOf()
        }

        fun getId() = model.id
            ?.also { idCache.add(it) }
            ?: throw IllegalStateException("No id exists in model")
    }

    fun execute(): List<UUID> {
        for (model in models)
            when (model) {
                is Kingdom -> TODO()
                is Role -> TODO()
                is User -> TODO()
            }
        return if (this::queriedIds.isInitialized)
            queriedIds
        else
            listOf()
    }

}