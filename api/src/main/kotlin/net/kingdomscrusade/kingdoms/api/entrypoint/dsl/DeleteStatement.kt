package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target.*
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
    operator fun ApiTarget.unaryMinus() { targets += this }

    fun execute() {
        for (target in targets) when {
            target is KingdomsTarget && target.type == TargetType.ID ->
                kingdomsService.value.deleteById(target.id!!)
            target is KingdomsTarget && target.type == TargetType.NAME ->
                kingdomsService.value.deleteByName(target.name!!)

            target is UsersTarget && target.type == TargetType.ID ->
                usersService.value.deleteById(target.id!!, target.kingdom)
            target is UsersTarget && target.type == TargetType.NAME ->
                usersService.value.deleteByName(target.name!!,target.kingdom)

            target is RolesTarget && target.type == TargetType.ID ->
                rolesService.value.deleteById(target.id!!)
            target is RolesTarget && target.type == TargetType.NAME ->
                rolesService.value.deleteByName(target.name!!, target.kingdom)
        }
    }
}