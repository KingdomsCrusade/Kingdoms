package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target.ApiTarget
import net.kingdomscrusade.kingdoms.api.model.ApiModel
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import javax.inject.Inject

class ReplaceStatement {

    // Injections
    @Inject lateinit var kingdomsService: Lazy<IKingdomsService>
    @Inject lateinit var usersService: Lazy<IUsersService>
    @Inject lateinit var rolesService: Lazy<IRolesService>

    private val map: MutableMap<ApiTarget, ApiModel> = mutableMapOf()
    @Suppress("UNUSED_PARAMETER")
    operator fun ApiTarget.compareTo(it: ApiModel): Int { TODO() }

    fun execute() { TODO() }

}