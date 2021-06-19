package net.kingdomscrusade.kingdoms.api.repository

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.Role
import net.kingdomscrusade.kingdoms.api.table.PermissionsTable
import net.kingdomscrusade.kingdoms.api.table.RolesTable
import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class RolesRepository : IRolesRepository {

    companion object : Provider<IRolesRepository> {
        @Provides
        override fun provide() : IRolesRepository = RolesRepository()
    }

    override fun create(_id: UUID, _name: String, _permissions: Set<PermissionType>, _kingdom: UUID?) {
        transaction {
            RolesTable.insert {
                it[id] = _id
                it[name] = _name
                it[kingdom] = _kingdom
            }[RolesTable.id].let { roleId ->
                for (_permission in _permissions)
                    PermissionsTable.insert {
                        it[ref] = roleId
                        it[value] = _permission
                    }
            }
        }
    }

    override fun readById(_id: UUID) = transaction {
        RolesTable
            .select { RolesTable.id eq _id }
            .map {
                Role(
                    id = it[RolesTable.id],
                    name = it[RolesTable.name],
                    kingdom = it[RolesTable.kingdom],
                    permissions = mutableSetOf()
                )
            }.also { roleList ->
                PermissionsTable
                    .select { PermissionsTable.ref eq _id }
                    .map { it[PermissionsTable.ref] to it[PermissionsTable.value] }
                    .forEach { permission ->
                        for (role in roleList)
                            if (role.id == permission.first)
                                role.permissions?.add(permission.second)
                    }
            }
    }

    override fun readByName(_name: String, _kingdom: UUID?) = transaction {
        RolesTable
            .select { (RolesTable.name eq _name) and (RolesTable.kingdom eq _kingdom) }
            .map { it[RolesTable.id] }
            .let { mutableListOf<Role>().apply {
                    it.forEach { _id -> readById(_id).forEach { add(it) } }
            } }
    }

    override fun updateById(_targetId: UUID, _name: String?, _permissions: Set<PermissionType>?, _kingdom: UUID?) {
        transaction {
            RolesTable.update({ RolesTable.id eq _targetId }) {
                if (!_name.isNullOrBlank()) it[name] = _name
                if (_kingdom != null) it[kingdom] = _kingdom
            }
            if (!_permissions.isNullOrEmpty()) {
                PermissionsTable.deleteIgnoreWhere { PermissionsTable.ref eq _targetId }
                _permissions.forEach { _permission ->
                    PermissionsTable.insert { it[ref] = _targetId; it[value] = _permission }
                }
            }
        }
    }

    override fun updateByName(
        _targetName: String,
        _targetRoleKingdom: UUID?,
        _name: String?,
        _permissions: Set<PermissionType>?,
        _kingdom: UUID?
    ) {
        transaction {
            RolesTable
                .select { (RolesTable.name eq _targetName) and (RolesTable.kingdom eq _targetRoleKingdom) }
                .map { it[RolesTable.id] }
                .let { idList -> idList.forEach { updateById(it, _name, _permissions, _kingdom) } }
        }
    }

    override fun deleteById(_id: UUID) {
        transaction { RolesTable.deleteWhere { RolesTable.id eq _id } }
        // Rows referencing the role in Permissions table will cascade on delete
    }

    override fun deleteByName(_name: String, _kingdom: UUID?) {
        transaction { RolesTable.deleteWhere { ( RolesTable.name eq _name ) and ( RolesTable.kingdom eq _kingdom ) } }
        // Rows referencing the role in Permissions table will cascade on delete
    }
}
