package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target.ApiTarget
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import javax.inject.Inject

class DeleteStatement {

    // Injections
    @Inject lateinit var kingdomsService: Lazy<IKingdomsService>
    @Inject lateinit var usersService: Lazy<IUsersService>
    @Inject lateinit var rolesService: Lazy<IRolesService>

    private val targets: MutableList<ApiTarget> = mutableListOf()
    operator fun ApiTarget.unaryMinus() { TODO() }

    fun execute() { TODO() }
}