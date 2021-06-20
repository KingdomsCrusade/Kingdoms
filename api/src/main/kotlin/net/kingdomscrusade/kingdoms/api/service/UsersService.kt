package net.kingdomscrusade.kingdoms.api.service

import dagger.Provides
import net.kingdomscrusade.kingdoms.api.miscellaneous.Provider
import net.kingdomscrusade.kingdoms.api.model.User
import net.kingdomscrusade.kingdoms.api.repository.IUsersRepository
import net.kingdomscrusade.kingdoms.api.table.RolesTable
import net.kingdomscrusade.kingdoms.api.table.UsersTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.inject.Inject

class UsersService : IUsersService{

    companion object : Provider<IUsersService> {
        @Provides
        override fun provide() : IUsersService = UsersService()
    }

    @Inject
    private lateinit var repository: IUsersRepository

    val kingdomDupe : (_id : UUID, _kingdom : UUID) -> Boolean = { _id, _kingdom ->
        transaction {
            RolesTable
                .slice(UsersTable.id)
                .select { UsersTable.kingdom eq _kingdom }
                .map { it[UsersTable.id] }
                .contains(_id)
        }
    }

    override fun create(_id: UUID, _name: String, _kingdom: UUID, _role: UUID?) {
        // User kingdom duplication check
        if (kingdomDupe(_id, _kingdom)) throw IllegalStateException("Same user under the kingdom exists")

        repository.create(_id, _name, _kingdom, _role)
    }

    override fun readById(_id: UUID, _kingdom: UUID): List<User> =
        repository.readById(_id, _kingdom)

    override fun readByName(_name: String, _kingdom: UUID): List<User> =
        repository.readByName(_name, _kingdom)

    override fun updateById(
        _targetId: UUID,
        _targetUserKingdom: UUID,
        _name: String?,
        _role: UUID?
    ) = repository.updateById(_targetId, _targetUserKingdom, _name, _role)

    override fun updateByName(
        _targetName: String,
        _targetUserKingdom: UUID,
        _name: String?,
        _role: UUID?
    ) = repository.updateByName(_targetName, _targetUserKingdom, _name, _role)

    override fun deleteById(_id: UUID, _kingdom: UUID) =
        repository.deleteById(_id, _kingdom)

    override fun deleteByName(_name: String, _kingdom: UUID) =
        repository.deleteByName(_name, _kingdom)
}