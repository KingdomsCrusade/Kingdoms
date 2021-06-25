package net.kingdomscrusade.kingdoms.api.repository

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.UserModel
import net.kingdomscrusade.kingdoms.api.table.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class UsersRepository : IUsersRepository {

    companion object : Provider<IUsersRepository> {
        @Provides
        override fun provide() : IUsersRepository = UsersRepository()
    }

    override fun create(_obj: UserModel) {
        TODO("Not yet implemented")
    }

    override fun readById(_id: UUID, _kingdom: UUID): List<UserModel> = transaction {
        UsersTable
            .select { (UsersTable.id eq _id) and (UsersTable.kingdom eq _kingdom) }
            .map {
                UserModel(
                    id = it[UsersTable.id],
                    name = it[UsersTable.name],
                    kingdom = it[UsersTable.kingdom],
                    role = it[UsersTable.role]
                )
            }
    }

    override fun readByName(_name: String, _kingdom: UUID): List<UserModel> = transaction {
        UsersTable
            .select { (UsersTable.name eq _name) and (UsersTable.kingdom eq _kingdom) }
            .map {
                UserModel(
                    id = it[UsersTable.id],
                    name = it[UsersTable.name],
                    kingdom = it[UsersTable.kingdom],
                    role = it[UsersTable.role]
                )
            }
    }

    override fun replaceById(_targetId: UUID, _targetUserKingdom: UUID, _obj: UserModel) {
        TODO("Not yet implemented")
    }

    override fun replaceByName(_targetName: String, _targetUserKingdom: UUID, _obj: UserModel) {
        TODO("Not yet implemented")
    }

    override fun deleteById(_id: UUID, _kingdom: UUID) {
        transaction { UsersTable.deleteWhere { (UsersTable.id eq _id) and (UsersTable.kingdom eq _kingdom) } }
    }

    override fun deleteByName(_name: String, _kingdom: UUID) {
        transaction { UsersTable.deleteWhere { (UsersTable.name eq _name) and (UsersTable.kingdom eq _kingdom) } }
    }
}
