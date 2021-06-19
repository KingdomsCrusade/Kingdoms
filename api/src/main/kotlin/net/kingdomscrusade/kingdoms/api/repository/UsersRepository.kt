package net.kingdomscrusade.kingdoms.api.repository

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.User
import net.kingdomscrusade.kingdoms.api.table.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UsersRepository : IUsersRepository {

    companion object : Provider<IUsersRepository> {
        @Provides
        override fun provide() : IUsersRepository = UsersRepository()
    }

    override fun create(_id: UUID, _name: String, _kingdom: UUID, _role: UUID?) {
        transaction {
            UsersTable.insert {
                it[id] = _id
                it[name] = _name
                it[kingdom] = _kingdom
                it[role] = _role
            }
        }
    }

    override fun readById(_id: UUID): List<User> = transaction {
        UsersTable
            .select { UsersTable.id eq _id }
            .map {
                User(
                    id = it[UsersTable.id],
                    name = it[UsersTable.name],
                    kingdom = it[UsersTable.kingdom],
                    role = it[UsersTable.role]
                )
            }
    }

    override fun readByName(_name: String, _kingdom: UUID): List<User> = transaction {
        UsersTable
            .select { (UsersTable.name eq _name) and (UsersTable.kingdom eq _kingdom) }
            .map {
                User(
                    id = it[UsersTable.id],
                    name = it[UsersTable.name],
                    kingdom = it[UsersTable.kingdom],
                    role = it[UsersTable.role]
                )
            }
    }

    override fun updateById(
        _targetId: UUID,
        _targetUserKingdom : UUID,
        _name: String?,
        _role: UUID?
    ) {
        transaction {
            UsersTable.update ({ ( UsersTable.id eq _targetId ) and ( UsersTable.kingdom eq _targetUserKingdom ) }) {
                if (!_name.isNullOrBlank()) it[name] = _name
                if (_role != null) it[role] = _role
            }
        }
    }

    override fun updateByName(
        _targetName: String,
        _targetUserKingdom: UUID,
        _name: String?,
        _role: UUID?
    ) {
        transaction {
            UsersTable.update ({ (UsersTable.name eq _targetName) and (UsersTable.kingdom eq _targetUserKingdom) }) {
                if (!_name.isNullOrBlank()) it[name] = _name
                if (_role != null) it[role] = _role
            }
        }
    }

    override fun deleteById(_id: UUID, _kingdom: UUID) {
        transaction { UsersTable.deleteWhere { (UsersTable.id eq _id) and (UsersTable.kingdom eq _kingdom) } }
    }

    override fun deleteByName(_name: String, _kingdom: UUID) {
        transaction { UsersTable.deleteWhere { (UsersTable.name eq _name) and (UsersTable.kingdom eq _kingdom) } }
    }
}
