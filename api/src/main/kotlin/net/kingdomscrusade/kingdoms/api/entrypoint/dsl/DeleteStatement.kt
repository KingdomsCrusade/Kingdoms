package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.model.ApiModels
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.model.User
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import javax.inject.Inject

class DeleteStatement {

    // Injections
    @Inject
    lateinit var kingdomsService: Lazy<IKingdomsService>

    @Inject
    lateinit var usersService: Lazy<IUsersService>

    @Inject
    lateinit var rolesService: Lazy<IRolesService>

    // Error messages
    private val kingdomError =
        "Kingdom deletion needs either \"id\" or \"name\" property to be present in order to execute"
    private val roleError =
        "Role deletion needs either \"id\" or \"name\" and \"kingdom\" property to be present in order to to execute"
    private val userError =
        "User deletion needs either \"id\" and \"kingdom\" or \"name\" and \"kingdom\" property to be present in order to execute"

    private val targets: MutableList<ApiModels> = mutableListOf()

    operator fun ApiModels.unaryMinus() {
        targets += this
    }

    fun execute() {
        for (target in targets) {
            when (target) {

                // Prioritize Id
                is Kingdom -> {
                    if (target.id != null) {
                        kingdomsService.value.deleteById(target.id!!)
                        break
                    } else
                        kingdomsService.value.deleteByName(
                            target.name
                                ?: throw NullPointerException(kingdomError)
                        )
                }

                is Role -> {
                    if (target.id != null) {
                        rolesService.value.deleteById(target.id!!)
                        break
                    } else
                        rolesService.value.deleteByName(
                            target.name
                                ?: throw NullPointerException(roleError),
                            target.kingdom
                        )
                }

                is User -> {
                    if (target.id != null && target.kingdom != null) {
                        usersService.value.deleteById(target.id!!, target.kingdom!!)
                        break
                    } else
                        usersService.value.deleteByName(
                            target.name
                                ?: throw NullPointerException(userError),
                            target.kingdom
                                ?: throw NullPointerException(userError)
                        )
                }

            }
        }
    }
}