package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.entrypoint.dsl.target.*
import net.kingdomscrusade.kingdoms.api.model.ApiModel
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import javax.inject.Inject

class ReadStatement {

    // Injections
    @Inject lateinit var kingdomsService: Lazy<IKingdomsService>
    @Inject lateinit var usersService: Lazy<IUsersService>
    @Inject lateinit var rolesService: Lazy<IRolesService>

    private val targets: MutableList<ApiTarget> = mutableListOf()
    operator fun ApiTarget.not() { targets += this }

    fun execute(): List<ApiModel> =
        mutableListOf<ApiModel>().apply {
            for (target in targets) when {
                target is KingdomsTarget && target.type == TargetType.ID ->
                    this += kingdomsService.value.readById(target.id!!)
                target is KingdomsTarget && target.type == TargetType.NAME ->
                    this += kingdomsService.value.readByName(target.name!!)

                target is UsersTarget && target.type == TargetType.ID ->
                    this += usersService.value.readById(target.id!!, target.kingdom)
                target is UsersTarget && target.type == TargetType.NAME ->
                    this += usersService.value.readByName(target.name!!, target.kingdom)

                target is RolesTarget && target.type == TargetType.ID ->
                    this += rolesService.value.readById(target.id!!)
                target is RolesTarget && target.type == TargetType.NAME ->
                    this += rolesService.value.readByName(target.name!!, target.kingdom)
            }
        }
}