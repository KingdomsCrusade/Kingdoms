package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target.*
import net.kingdomscrusade.kingdoms.api.model.ApiModel
import net.kingdomscrusade.kingdoms.api.model.KingdomsModel
import net.kingdomscrusade.kingdoms.api.model.RolesModel
import net.kingdomscrusade.kingdoms.api.model.UsersModel
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
    operator fun ApiTarget.compareTo(it: ApiModel): Int {map += this to it; return 1}

    fun execute() {
        for ((target, model) in map) when {
            target is KingdomsTarget && model is KingdomsModel && target.type == TargetType.ID ->
                kingdomsService.value.replaceById(target.id!!, model)
            target is KingdomsTarget && model is KingdomsModel && target.type == TargetType.NAME ->
                kingdomsService.value.replaceByName(target.name!!, model)

            target is UsersTarget && model is UsersModel && target.type == TargetType.ID ->
                usersService.value.replaceById(target.id!!, target.kingdom, model)
            target is UsersTarget && model is UsersModel && target.type == TargetType.NAME ->
                usersService.value.replaceByName(target.name!!, target.kingdom, model)

            target is RolesTarget && model is RolesModel && target.type == TargetType.ID ->
                rolesService.value.replaceById(target.id!!, model)
            target is RolesTarget && model is RolesModel && target.type == TargetType.NAME ->
                rolesService.value.replaceByName(target.name!!, target.kingdom, model)

            else -> throw IllegalArgumentException("The argument pair does not match to perform a replace statement.")
        }
    }

}