package net.kingdomscrusade.kingdoms.api.service

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.repository.IRolesRepository
import net.kingdomscrusade.kingdoms.api.table.RolesTable
import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
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

    override fun create(_id: UUID, _name: String, _permissions: Set<PermissionType>, _kingdom: UUID?) {
        // Role name duplication check
        if (nameDupe(_name, _kingdom)) throw IllegalStateException("Role with the same name under the kingdom exists")

        repository.create(_id, _name, _permissions, _kingdom)
    }

    override fun readById(_id: UUID): List<Role> =
        repository.readById(_id)

    override fun readByName(_name: String, _kingdom: UUID?): List<Role> =
        repository.readByName(_name, _kingdom)
    override fun updateById(_targetId: UUID, _name: String?, _permissions: Set<PermissionType>?, _kingdom: UUID?) {
        // Role name duplication check
        if (nameDupe(_name, _kingdom)) throw IllegalStateException("Role with the same name under the kingdom exists")

        repository.updateById(_targetId, _name, _permissions, _kingdom)
    }

    override fun updateByName(
        _targetName: String,
        _targetRoleKingdom: UUID?,
        _name: String?,
        _permissions: Set<PermissionType>?,
        _kingdom: UUID?
    ) {
        // Role name duplication check
        if (nameDupe(_name, _kingdom)) throw IllegalStateException("Role with the same name under the kingdom exists")

        repository.updateByName(_targetName, _targetRoleKingdom, _name, _permissions, _kingdom)
    }

    override fun deleteById(_id: UUID) =
        repository.deleteById(_id)

    override fun deleteByName(_name: String, _kingdom: UUID?) =
        repository.deleteByName(_name, _kingdom)
}