package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.model.ApiModel
import net.kingdomscrusade.kingdoms.api.model.KingdomsModel
import net.kingdomscrusade.kingdoms.api.model.RolesModel
import net.kingdomscrusade.kingdoms.api.model.UsersModel
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

    private val modelList: MutableList<ApiModel> = mutableListOf()
    private val cacheMap: MutableMap<Pair<Class<ApiModel>, String>, UUID> = mutableMapOf()
    operator fun ApiModel.unaryPlus() {
        modelList += this
        cacheMap += Pair(this.javaClass, this.name) to this.id
    }

    fun execute(): MutableMap<Pair<Class<ApiModel>, String>, UUID> {
        for (model in modelList) when (model) {
            is KingdomsModel -> kingdomsService.value.create(model)
            is UsersModel -> usersService.value.create(model)
            is RolesModel -> rolesService.value.create(model)
        }
        return cacheMap
    }

}