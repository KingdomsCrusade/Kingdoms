package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.KingdomsApi
import net.kingdomscrusade.kingdoms.api.model.ApiModels
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.model.User
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import javax.inject.Inject

@Suppress("unused") // Because it's required to have database initialized & connected for this to work
fun KingdomsApi.update(init: UpdateStatement.() -> Unit) =
    UpdateStatement().let {
        it.init()
        it.execute()
    }

class UpdateStatement {
    // Injections
    @Inject
    lateinit var kingdomsService: Lazy<IKingdomsService>

    @Inject
    lateinit var usersService: Lazy<IUsersService>

    @Inject
    lateinit var rolesService: Lazy<IRolesService>

    private val map: MutableMap<ApiModels, ApiModels> = mutableMapOf()

    // Error messages
    private val kingdomError =
        "Kingdom read needs either \"id\" or \"name\" property to be present as target in order to execute"
    private val roleError =
        "Role read needs either \"id\" or \"name\" and \"kingdom\" property to be present as target in order to to execute"
    private val userError =
        "User read needs either \"id\" and \"kingdom\" or \"name\" and \"kingdom\" property to be present as target in order to execute"

    operator fun ApiModels.compareTo(it: ApiModels): Int {
        if (
//            ((this is Kingdom) and (it is Kingdom)) or
//            ((this is Role) and (it is Role)) or
//            ((this is User) and (it is User))
            this::class == it
        )
            map += this to it
        else
            throw IllegalArgumentException("Both values must have same types")

        return 1
    }


    fun execute() {
        for ((target, change) in map) {
            when (target) {

                // Prioritize Id
                is Kingdom -> {

                    if (change !is Kingdom) throw IllegalStateException("Type of target and change is different.")

                    if (target.id != null) {
                        kingdomsService.value.updateById(target.id!!, change.name)
                        break
                    } else
                        kingdomsService.value.updateByName(
                            target.name
                                ?: throw NullPointerException(kingdomError),
                            change.name
                        )
                }

                is Role -> {

                    if (change !is Role) throw IllegalStateException("Type of target and change is different.")

                    if (target.id != null) {
                        rolesService.value.updateById(target.id!!, change.name, change.permissions, change.kingdom)
                        break
                    } else
                        rolesService.value.updateByName(
                            target.name
                                ?: throw NullPointerException(roleError),
                            target.kingdom, change.name, change.permissions, change.kingdom
                        )
                }

                is User -> {

                    if (change !is User) throw IllegalStateException("Type of target and change is different.")

                    if (target.id != null && target.kingdom != null) {
                        usersService.value.updateById(target.id!!, target.kingdom!!, change.name, change.role)
                        break
                    } else
                        usersService.value.updateByName(
                            target.name
                                ?: throw NullPointerException(userError),
                            target.kingdom
                                ?: throw NullPointerException(userError),
                            change.name, change.role
                        )
                }

            }
        }
    }


}