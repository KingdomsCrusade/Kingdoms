package net.kingdomscrusade.kingdoms.api.repository

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.RoleModel
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

    override fun create(_obj: RoleModel) {
        TODO("Not yet implemented")
    }

    override fun readById(_id: UUID) = transaction {
        RolesTable
            .select { RolesTable.id eq _id }
            .map {
                RoleModel(
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
            .let { mutableListOf<RoleModel>().apply {
                    it.forEach { _id -> readById(_id).forEach { add(it) } }
            } }
    }

    override fun replaceById(_targetId: UUID, _obj: RoleModel) {
        TODO("Not yet implemented")
    }

    override fun replaceByName(_targetName: String, _targetRoleKingdom: UUID?, _obj: RoleModel) {
        TODO("Not yet implemented")
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
