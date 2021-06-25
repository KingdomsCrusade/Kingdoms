package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.model.ApiModel
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import java.util.*
import javax.inject.Inject

class CreateStatement {

    // Injections
    @Inject lateinit var kingdomsService: Lazy<IKingdomsService>
    @Inject lateinit var usersService: Lazy<IUsersService>
    @Inject lateinit var rolesService: Lazy<IRolesService>

    private val models: MutableList<ApiModel> = mutableListOf()
    private val idList: MutableList<UUID> = mutableListOf()
    operator fun ApiModel.unaryPlus() { TODO() }

    fun execute(): List<UUID> { TODO() }

}