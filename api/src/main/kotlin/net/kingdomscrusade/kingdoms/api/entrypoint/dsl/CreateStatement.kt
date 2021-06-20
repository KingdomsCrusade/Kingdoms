package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.model.User
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import java.util.*
import javax.inject.Inject

@Suppress("unused") // Because it's required to have database initialized & connected for this to work
fun KingdomsApi.create(init: CreateStatement.() -> Unit): List<UUID> =
    CreateStatement().let {
        it.init()
        it.execute()
    }

class CreateStatement {

    // Injections
    @Inject
    lateinit var kingdomsService: Lazy<IKingdomsService>

    @Inject
    lateinit var usersService: Lazy<IUsersService>

    @Inject
    lateinit var rolesService: Lazy<IRolesService>

    private val models: MutableList<ApiModels> = mutableListOf()
    private val idList: MutableList<UUID> = mutableListOf()

    operator fun ApiModels.unaryPlus() {
        models += this
        idList += id!!
    }

    // Will throw NPE is anything required is uninitialized
    fun execute(): List<UUID> {
        for (model in models) {
            when (model) {
                is Kingdom ->
                    kingdomsService.value.create(model.id!!, model.name!!)
                is Role ->
                    rolesService.value.create(model.id!!, model.name!!, model.permissions!!.toSet(), model.kingdom)
                is User ->
                    usersService.value.create(model.id!!, model.name!!, model.kingdom!!, model.role)
            }
        }
        return idList
    }

}