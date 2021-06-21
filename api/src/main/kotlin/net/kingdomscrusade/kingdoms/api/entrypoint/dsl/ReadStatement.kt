package net.kingdomscrusade.kingdoms.api.entrypoint.dsl

import net.kingdomscrusade.kingdoms.api.model.ApiModels
import net.kingdomscrusade.kingdoms.api.model.Kingdom
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.model.User
import net.kingdomscrusade.kingdoms.api.service.IKingdomsService
import net.kingdomscrusade.kingdoms.api.service.IRolesService
import net.kingdomscrusade.kingdoms.api.service.IUsersService
import javax.inject.Inject

class ReadStatement {

    // Injections
    @Inject
    lateinit var kingdomsService: Lazy<IKingdomsService>

    @Inject
    lateinit var usersService: Lazy<IUsersService>

    @Inject
    lateinit var rolesService: Lazy<IRolesService>

    private val targets: MutableList<ApiModels> = mutableListOf()

    // Error messages
    private val kingdomError =
        "Kingdom read needs either \"id\" or \"name\" property to be present in order to execute"
    private val roleError =
        "Role read needs either \"id\" or \"name\" and \"kingdom\" property to be present in order to to execute"
    private val userError =
        "User read needs either \"id\" and \"kingdom\" or \"name\" and \"kingdom\" property to be present in order to execute"

    operator fun ApiModels.not() {
        targets += this
    }


    fun execute(): List<ApiModels> {

        val list: MutableList<ApiModels> = mutableListOf()

        for (target in targets) {
            when (target) {

                // Prioritize Id
                is Kingdom -> {
                    if (target.id != null) {
                        list += kingdomsService.value.readById(target.id!!)
                        break
                    } else
                        list += kingdomsService.value.readByName(
                            target.name
                                ?: throw NullPointerException(kingdomError)
                        )
                }

                is Role -> {
                    if (target.id != null) {
                        list += rolesService.value.readById(target.id!!)
                        break
                    } else
                        list += rolesService.value.readByName(
                            target.name
                                ?: throw NullPointerException(roleError),
                            target.kingdom
                        )
                }

                is User -> {
                    if (target.id != null && target.kingdom != null) {
                        list += usersService.value.readById(target.id!!, target.kingdom!!)
                        break
                    } else
                        list += usersService.value.readByName(
                            target.name
                                ?: throw NullPointerException(userError),
                            target.kingdom
                                ?: throw NullPointerException(userError)
                        )
                }

            }
        }
        return list
    }
}