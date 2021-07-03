package net.kingdomscrusade.kingdoms.api.repository

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.RolesModel
import net.kingdomscrusade.kingdoms.api.table.PermissionsTable
import net.kingdomscrusade.kingdoms.api.table.RolesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class RolesRepository : IRolesRepository {

    companion object : Provider<IRolesRepository> {
        @Provides
        override fun provide() : IRolesRepository = RolesRepository()
    }

    override fun create(_obj: RolesModel) {
        RolesTable.insert {
            it[id] = _obj.id
            it[name] = _obj.name
            it[kingdom] = _obj.kingdom
        }
        for (permission in _obj.permissions)
            PermissionsTable.insert {
                it[ref] = _obj.id
                it[value] = permission
            }
    }

    override fun readById(_id: UUID) = transaction {
        RolesTable
            .select { RolesTable.id eq _id }
            .map {
                RolesModel(
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
                                role.permissions.add(permission.second)
                    }
            }
    }

    override fun readByName(_name: String, _kingdom: UUID?) = transaction {
        RolesTable
            .select { (RolesTable.name eq _name) and (RolesTable.kingdom eq _kingdom) }
            .map { it[RolesTable.id] }
            .let { mutableListOf<RolesModel>().apply {
                    it.forEach { _id -> readById(_id).forEach { add(it) } }
            } }
    }

    override fun replaceById(_targetId: UUID, _obj: RolesModel) {
        replaceFunction(_targetId, _obj)
    }

    override fun replaceByName(_targetName: String, _targetRoleKingdom: UUID?, _obj: RolesModel) {
        RolesTable
            .select { (RolesTable.name eq _targetName) and (RolesTable.kingdom eq _targetRoleKingdom) }
            .map { it[RolesTable.id] }
            .forEach {
                replaceFunction(it, _obj)
            }
    }

    private fun replaceFunction(_targetId: UUID, _obj: RolesModel) {
        RolesTable.update({ RolesTable.id eq _targetId }) {
            it[id] = _obj.id
            it[name] = _obj.name
            it[kingdom] = _obj.kingdom
        }
        PermissionsTable.deleteWhere { PermissionsTable.ref eq _targetId }
        for (permission in _obj.permissions)
            PermissionsTable.insert {
                it[ref] = _obj.id
                it[value] = permission
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
