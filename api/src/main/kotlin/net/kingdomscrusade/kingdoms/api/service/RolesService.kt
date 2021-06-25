package net.kingdomscrusade.kingdoms.api.service

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.RoleModel
import net.kingdomscrusade.kingdoms.api.repository.IRolesRepository
import net.kingdomscrusade.kingdoms.api.table.RolesTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.inject.Inject

class RolesService : IRolesService{

    companion object : Provider<IRolesService> {
        @Provides
        override fun provide() : IRolesService = RolesService()
    }

    @Inject
    private lateinit var repository: IRolesRepository

    val nameDupe : (String?, UUID?) -> Boolean = { _name, _kingdom ->
        transaction {
            if (_kingdom != null)
                RolesTable
                    .slice(RolesTable.name)
                    .select { RolesTable.kingdom eq _kingdom }
                    .map { it[RolesTable.name] }
                    .contains(_name)
            else false
        }
    }

    override fun create(_obj: RoleModel) {
        TODO("Not yet implemented")
    }

    override fun readById(_id: UUID): List<RoleModel> =
        repository.readById(_id)

    override fun readByName(_name: String, _kingdom: UUID?): List<RoleModel> =
        repository.readByName(_name, _kingdom)

    override fun replaceById(_targetId: UUID, _obj: RoleModel) {
        TODO("Not yet implemented")
    }

    override fun replaceByName(_targetName: String, _targetRoleKingdom: UUID?, _obj: RoleModel) {
        TODO("Not yet implemented")
    }

    override fun deleteById(_id: UUID) =
        repository.deleteById(_id)

    override fun deleteByName(_name: String, _kingdom: UUID?) =
        repository.deleteByName(_name, _kingdom)
}